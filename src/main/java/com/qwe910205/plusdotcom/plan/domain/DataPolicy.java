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

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Plan plan;

    @AttributeOverride(name = "value", column = @Column(name = "SERVING_DATA_QUANTITY"))
    @Embedded
    private ServingDataQuantity servingDataQuantity;

    @SortNatural
    @OneToMany(mappedBy = "dataPolicy",cascade = CascadeType.ALL, orphanRemoval = true)
    private final SortedSet<DataPolicyDetail> dataPolicyDetails = new TreeSet<>();

    public DataPolicy(Plan plan, Integer dataQuantity) {
        this.plan = plan;
        if (Objects.nonNull(dataQuantity))
            this.servingDataQuantity = new ServingDataQuantity(dataQuantity);
    }

    public void addDataPolicyDetailThatHasNotAdditionalCharge(int dataBoundary, Long speedLimit) {
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(this, dataBoundary, speedLimit);
        this.dataPolicyDetails.remove(dataPolicyDetail);
        this.dataPolicyDetails.add(dataPolicyDetail);
    }

    public void addDataPolicyDetail(int dataBoundary, Long speedLimit, int dataUnit, double cost, Integer maxCost) {
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(this, dataBoundary, speedLimit);
        dataPolicyDetail.setChargePolicyAboutExcessDataUsage(new ChargePolicyAboutExcessDataUsage(dataUnit, cost, maxCost));
        this.dataPolicyDetails.remove(dataPolicyDetail);
        this.dataPolicyDetails.add(dataPolicyDetail);
    }

    public int getServingDataQuantity() {
        return servingDataQuantity.getValue();
    }

    public boolean isUnlimited() {
        return Objects.isNull(servingDataQuantity);
    }

    public long getAdditionalChargeAbout(long dataUsage) {
        int additionalCharge = 0;
        long remainData = dataUsage - servingDataQuantity.getValue();
        if (remainData <= 0)
            return additionalCharge;
        List<Integer> dataBoundaries = dataPolicyDetails.stream().map(DataPolicyDetail::getDataBoundary).toList();
        List<DataPolicyDetail> dataPolicyDetailList = dataPolicyDetails.stream().toList();
        for (int index = 0; index < dataBoundaries.size(); index++) {
            if (remainData <= 0)
                return additionalCharge;
            long checkData;
            if (index < dataBoundaries.size() - 1 && dataPolicyDetailList.get(index).getDataBoundary() + remainData > dataPolicyDetailList.get(index + 1).getDataBoundary()) {
                checkData = dataPolicyDetailList.get(index + 1).getDataBoundary() - dataPolicyDetailList.get(index).getDataBoundary();
            } else {
                checkData = remainData;
            }
            remainData -= checkData;
            DataPolicyDetail dataPolicyDetail = dataPolicyDetailList.get(index);
            long charge = dataPolicyDetail.getChargeAbout(checkData);
            additionalCharge += charge;
        }
        return additionalCharge;
    }
}
