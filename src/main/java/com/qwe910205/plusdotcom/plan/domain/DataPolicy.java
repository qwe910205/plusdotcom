package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.plan.domain.wrapper.ServingDataQuantity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DataPolicy {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Plan plan;

    @AttributeOverride(name = "value", column = @Column(name = "SERVING_DATA_QUANTITY"))
    @Embedded
    private ServingDataQuantity servingDataQuantity;

    @SortNatural
    @OneToMany(mappedBy = "dataPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    private final SortedSet<DataPolicyDetail> dataPolicyDetails = new TreeSet<>();

    public DataPolicy(Plan plan, Integer dataQuantity) {
        this.plan = plan;
        if (Objects.nonNull(dataQuantity))
            this.servingDataQuantity = new ServingDataQuantity(dataQuantity);
    }

    public int getServingDataQuantity() {
        return servingDataQuantity.getValue();
    }

    public void addDataPolicyDetailThatHasNotAdditionalCharge(int dataBoundary, Long speedLimit) {
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(this, dataBoundary, speedLimit);
        addDataPolicyDetail(dataPolicyDetail);
    }

    public void addDataPolicyDetail(int dataBoundary, Long speedLimit, int dataUnit, double cost, Integer maxCost) {
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(this, dataBoundary, speedLimit);
        dataPolicyDetail.setChargePolicyAboutExcessDataUsage(new ChargePolicyAboutExcessDataUsage(dataUnit, cost, maxCost));
        addDataPolicyDetail(dataPolicyDetail);
    }

    private void addDataPolicyDetail(DataPolicyDetail dataPolicyDetail) {
        if (isUnlimited())
            throw new IllegalArgumentException("무제한 데이터 정책에는 세부사항을 추가할 수 없습니다.");
        if (dataPolicyDetail.getDataBoundary() != 0 && !hasDataPolicyDetailThatHasDataBoundaryOfZero())
            throw new IllegalArgumentException("데이터 정책에 세부사항을 추가하려면 데이터 경곗값이 0인 세부사항을 가장 먼저 추가해야 합니다.");
        if (dataPolicyDetails.size() >= 1
                && dataPolicyDetails.last().getDataBoundary() < dataPolicyDetail.getDataBoundary()
                && dataPolicyDetails.last().hasSpeedLimit())
            throw new IllegalArgumentException("데이터 정책의 세부사항 중 마지막 세부사항만 속도 제한을 가질 수 있습니다.");

        this.dataPolicyDetails.remove(dataPolicyDetail);
        this.dataPolicyDetails.add(dataPolicyDetail);
    }

    private boolean hasDataPolicyDetailThatHasDataBoundaryOfZero() {
        return dataPolicyDetails.contains(new DataPolicyDetail(this, 0, null));
    }

    public boolean isUnlimited() {
        return Objects.isNull(servingDataQuantity);
    }

    public long getAdditionalChargeAbout(long dataUsage) {
        long additionalCharge = 0;
        if (isUnlimited()) return additionalCharge;

        long remainDataUsage = dataUsage - servingDataQuantity.getValue();
        if (remainDataUsage <= 0)
            return additionalCharge;

        List<ObjectThatGivesCharge> objectsThatGivesCharge = getObjectsThatGivesCharge(remainDataUsage);
        for (ObjectThatGivesCharge objectThatGivesCharge : objectsThatGivesCharge) {
            additionalCharge += objectThatGivesCharge.getCharge();
        }
        return additionalCharge;
    }

    private List<ObjectThatGivesCharge> getObjectsThatGivesCharge(long remainDataUsage) {
        List<ObjectThatGivesCharge> result = new ArrayList<>();
        if (dataPolicyDetails.size() == 0)
            return result;

        long point = 0;
        List<DataPolicyDetail> dataPolicyDetailList = dataPolicyDetails.stream().toList();
        List<Integer> dataBoundaries = getDataBoundariesExceptFirst();
        for (int dataBoundary : dataBoundaries) {
            long dataUsage = Math.min(remainDataUsage - point, dataBoundary - point);
            ObjectThatGivesCharge objectThatGivesCharge = new ObjectThatGivesCharge(dataPolicyDetailList.get(result.size()), dataUsage);
            result.add(objectThatGivesCharge);
            point = dataBoundary;
        }
        result.add(new ObjectThatGivesCharge(dataPolicyDetailList.get(result.size()), remainDataUsage - point));
        return result;
    }

    private List<Integer> getDataBoundariesExceptFirst() {
        if (dataPolicyDetails.size() == 0)
            return new ArrayList<>();

        return dataPolicyDetails.stream()
                .map(DataPolicyDetail::getDataBoundary)
                .toList()
                .subList(1, dataPolicyDetails.size());
    }

    public double getMaxDataUsage() {
        DataPolicyDetail last = dataPolicyDetails.last();
        if (last.canUseData())
            return Double.POSITIVE_INFINITY;
        return servingDataQuantity.getValue() + last.getDataBoundary();
    }

    public double availableAmountOfDataWithoutSpeedLimitWhenPayFor(long payment) {
        if (isUnlimited()) return Double.POSITIVE_INFINITY;

        double amountOfData = servingDataQuantity.getValue();
        List<Integer> boundaries = new ArrayList<>(List.of(0));
        boundaries.addAll(getDataBoundariesExceptFirst());
        List<Long> maxCharges = new ArrayList<>();
        List<Long> maxDataUsages = new ArrayList<>();
        List<DataPolicyDetail> dataPolicyDetailList = dataPolicyDetails.stream().toList();

        for (int index = 1; index < boundaries.size(); index++) {
            long dataUsage = boundaries.get(index) - boundaries.get(index - 1);
            maxDataUsages.add(dataUsage);
            DataPolicyDetail dataPolicyDetail = dataPolicyDetailList.get(index - 1);
            long charge = dataPolicyDetail.getChargeAbout(dataUsage);
            maxCharges.add(charge);
        }

        for (int index = 0; index < maxCharges.size(); index++) {
            long innerPayment = Math.min(maxCharges.get(index), payment);
            long maxDataUsage = maxDataUsages.get(index);
            amountOfData += Math.min(maxDataUsage, dataPolicyDetailList.get(index).availableAmountOfDataWithoutSpeedLimitWhenPayFor(innerPayment));
            payment -= innerPayment;
            if (payment <= 0) break;
        }
        if (payment > 0)
            amountOfData += dataPolicyDetails.last().availableAmountOfDataWithoutSpeedLimitWhenPayFor(payment);

        return amountOfData;
    }

    public double availableAmountOfDataWhenPayFor(long payment) {
        if (isUnlimited()) return Double.POSITIVE_INFINITY;

        double amountOfData = servingDataQuantity.getValue();
        List<Integer> boundaries = new ArrayList<>(List.of(0));
        boundaries.addAll(getDataBoundariesExceptFirst());
        List<Long> maxCharges = new ArrayList<>();
        List<Long> maxDataUsages = new ArrayList<>();
        List<DataPolicyDetail> dataPolicyDetailList = dataPolicyDetails.stream().toList();

        for (int index = 1; index < boundaries.size(); index++) {
            long dataUsage = boundaries.get(index) - boundaries.get(index - 1);
            maxDataUsages.add(dataUsage);
            DataPolicyDetail dataPolicyDetail = dataPolicyDetailList.get(index - 1);
            long charge = dataPolicyDetail.getChargeAbout(dataUsage);
            maxCharges.add(charge);
        }

        for (int index = 0; index < maxCharges.size(); index++) {
            long innerPayment = Math.min(maxCharges.get(index), payment);
            long maxDataUsage = maxDataUsages.get(index);
            amountOfData += Math.min(maxDataUsage, dataPolicyDetailList.get(index).availableAmountOfDataWhenPayFor(innerPayment));
            payment -= innerPayment;
            if (payment <= 0) break;
        }
        if (payment > 0)
            amountOfData += dataPolicyDetails.last().availableAmountOfDataWhenPayFor(payment);

        return amountOfData;
    }

    private record ObjectThatGivesCharge(DataPolicyDetail dataPolicyDetail, long checkData) {

        private long getCharge() {
            return dataPolicyDetail.getChargeAbout(checkData);
        }
    }
}
