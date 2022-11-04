package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.phone.domain.wrapper.Money;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanName;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(of = {"planId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Plan {

    @Id
    @GeneratedValue
    private Long id;

    @AttributeOverride(name = "value", column = @Column(name = "PLAN_ID", unique = true, nullable = false, updatable = false))
    @Embedded
    private PlanId planId;

    @AttributeOverride(name = "value", column = @Column(name = "NAME", nullable = false, unique = true))
    @Embedded
    private PlanName name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private NetworkTech networkTech;

    @AttributeOverride(name = "value", column = @Column(name = "BASIC_MONTHLY_CHARGE", nullable = false))
    @Embedded
    private Money basicMonthlyCharge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PlanCategory category;

    @MapKeyEnumerated(EnumType.STRING)
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Map<DataPolicyUnitPeriod, DataPolicy> dataPolicies = new HashMap<>();

    @Embedded
    private PlanDescription description;

    @ManyToMany
    private final Set<PremiumService> premiumServices = new HashSet<>();

    @ManyToMany
    private final Set<MediaService> mediaServices = new HashSet<>();

    @Builder
    private Plan(String planId, String name, String networkTech, int basicMonthlyCharge, String category) {
        Objects.requireNonNull(planId, "요금제의 아이디는 필수입니다.");
        Objects.requireNonNull(name, "요금제의 이름은 필수입니다.");
        Objects.requireNonNull(networkTech, "요금제의 통신 기술은 필수입니다.");
        Objects.requireNonNull(category, "요금제의 카테고리는 필수입니다");
        this.planId = new PlanId(planId);
        this.name = new PlanName(name);
        this.networkTech = new NetworkTech(networkTech);
        this.basicMonthlyCharge = new Money(basicMonthlyCharge);
        this.category = new PlanCategory(category);
    }

    public String getPlanId() {
        return planId.getValue();
    }

    public String getName() {
        if (Objects.isNull(name))
            return null;
        return name.getValue();
    }

    public String getNetworkTech() {
        if (Objects.isNull(networkTech))
            return null;
        return networkTech.getName();
    }

    public Integer getBasicMonthlyCharge() {
        if (Objects.isNull(basicMonthlyCharge))
            return null;
        return basicMonthlyCharge.getValue();
    }

    public String getCategory() {
        if (Objects.isNull(category))
            return null;
        return category.getName();
    }

    public void setDescription(PlanDescription planDescription) {
        this.description = planDescription;
    }

    public PlanDescription getDescription() {
        return description;
    }

    public DataPolicy getDataPolicy(DataPolicyUnitPeriod unitPeriod) {
        if (!dataPolicies.containsKey(unitPeriod))
            throw new NoSuchElementException(unitPeriod + " 단위의 데이터 정책은 등록되지 않았습니다.");
        return dataPolicies.get(unitPeriod);
    }

    public void putUnLimitedDataPolicy(DataPolicyUnitPeriod unitPeriod) {
        DataPolicy dataPolicy = new DataPolicy(this, null);
        putDataPolicy(unitPeriod, dataPolicy);
    }

    public void putLimitedDataPolicy(DataPolicyUnitPeriod unitPeriod, int dataQuantity) {
        DataPolicy dataPolicy = new DataPolicy(this, dataQuantity);
        putDataPolicy(unitPeriod, dataPolicy);
    }

    private void putDataPolicy(DataPolicyUnitPeriod unitPeriod, DataPolicy dataPolicy) {
        if (hasUnlimitedDataPolicy())
            throw new IllegalArgumentException("무제한 데이터 정책을 가진 요금제는 다른 데이터 정책을 추가할 수 없습니다.");

        dataPolicies.put(unitPeriod, dataPolicy);
    }

    private boolean hasUnlimitedDataPolicy() {
        return dataPolicies.values().stream().anyMatch(DataPolicy::isUnlimited);
    }

    public void addDataPolicyDetailThatHasNotAdditionalCharge(DataPolicyUnitPeriod unitPeriod, int dataBoundary, Long speedLimit) {
        DataPolicy dataPolicy = dataPolicies.get(unitPeriod);
        dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(dataBoundary, speedLimit);
    }

    public void addDataPolicyDetail(DataPolicyUnitPeriod unitPeriod, int dataBoundary, Long speedLimit, int dataUnit, double cost, Integer maximumCharge) {
        DataPolicy dataPolicy = dataPolicies.get(unitPeriod);
        dataPolicy.addDataPolicyDetail(dataBoundary, speedLimit, dataUnit, cost, maximumCharge);
    }

    public List<PremiumService> getPremiumServices() {
        return premiumServices.stream().toList();
    }

    public void addPremiumService(PremiumService premiumService) {
        premiumServices.remove(premiumService);
        premiumServices.add(premiumService);
    }

    public List<MediaService> getMediaServices() {
        return mediaServices.stream().toList();
    }

    public void addMediaService(MediaService mediaService) {
        mediaServices.remove(mediaService);
        mediaServices.add(mediaService);
    }

    public long getChargeAboutMonthlyDataUsage(long dataUsage) {
        if (canCalculateThingsRelatedToMonth())
            throw new RuntimeException(planId + " 요금제는 월간 데이터 정책 이외의 데이터 정책을 가지고 있어서 데이터 사용량에 따른 한 달간 요금을 계산할 수 없습니다.");

        if (getMaxMonthlyDataUsage() < dataUsage)
            throw new IllegalArgumentException("요금제의 월간 최대 데이터 사용량은 " + (int) getMaxMonthlyDataUsage() + "MB 입니다.");

        DataPolicy dataPolicy = dataPolicies.get(DataPolicyUnitPeriod.MONTH);

        long additionalCharge = dataPolicy.getAdditionalChargeAbout(dataUsage);
        return basicMonthlyCharge.getValue() + additionalCharge;
    }

    public boolean canCalculateThingsRelatedToMonth() {
        Set<DataPolicyUnitPeriod> dataPolicyUnitPeriods = dataPolicies.keySet();
        return !(dataPolicyUnitPeriods.contains(DataPolicyUnitPeriod.MONTH) && dataPolicyUnitPeriods.size() == 1);
    }

    public double getMaxMonthlyDataUsage() {
        if (canCalculateThingsRelatedToMonth())
            throw new RuntimeException(planId + " 요금제는 월간 데이터 정책 이외의 데이터 정책을 가지고 있어서 한 달간 최대로 사용할 수 있는 데이터양을 계산할 수 없습니다.");

        DataPolicy dataPolicy = dataPolicies.get(DataPolicyUnitPeriod.MONTH);
        return dataPolicy.getMaxDataUsage();
    }

    public double monthlyAmountOfDataWithoutSpeedLimitAt(int cost) {
        if (canCalculateThingsRelatedToMonth())
            throw new RuntimeException(planId + " 요금제는 월간 데이터 정책 이외의 데이터 정책을 가지고 있어서 비용에 따른 한 달간 사용할 수 있는 무제한 속도의 데이터 양을 계산할 수 없습니다.");

        if (cost < basicMonthlyCharge.getValue())
            return 0;
        DataPolicy dataPolicy = dataPolicies.get(DataPolicyUnitPeriod.MONTH);
        if (dataPolicy.isUnlimited()) return Double.POSITIVE_INFINITY;
        return dataPolicy.getServingDataQuantity() + dataPolicy.availableAdditionalUnlimitedAmountOfDataAt(cost - basicMonthlyCharge.getValue());
    }

    public double monthlyAmountOfDataAtCost(int cost) {
        return 0;
    }
}
