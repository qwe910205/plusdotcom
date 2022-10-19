package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.plan.domain.wrapper.MonthlyPayment;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanName;
import lombok.*;
import org.springframework.validation.ObjectError;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(of = {"planId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Plan {

    @Id @GeneratedValue
    private Long id;

    @AttributeOverride(name = "id", column = @Column(name = "PLAN_ID", unique = true, nullable = false, updatable = false))
    @Embedded
    private PlanId planId;

    @AttributeOverride(name = "name", column = @Column(nullable = false, unique = true))
    @Embedded
    private PlanName name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private NetworkTech networkTech;

    @AttributeOverride(name = "monthlyPayment", column = @Column(nullable = false))
    @Embedded
    private MonthlyPayment monthlyPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PlanCategory category;

    @MapKeyEnumerated(EnumType.STRING)
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<DataPolicyUnitPeriod, DataPolicy> dataPolicies = new HashMap<>();

    @Embedded
    private PlanDescription description;

    @ManyToMany
    private List<PremiumService> premiumServices = new ArrayList<>();

    @ManyToMany
    private List<MediaService> mediaServices = new ArrayList<>();

    @Builder
    public Plan(String id, String name, String networkTech, int monthlyPayment, String category) {
        Objects.requireNonNull(id, "요금제의 아이디는 필수입니다.");
        Objects.requireNonNull(name, "요금제의 이름은 필수입니다.");
        Objects.requireNonNull(networkTech, "요금제의 통신 기술은 필수입니다.");
        Objects.requireNonNull(category, "요금제의 카테고리는 필수입니다");
        this.planId = new PlanId(id);
        this.name = new PlanName(name);
        this.networkTech = new NetworkTech(networkTech);
        this.monthlyPayment = new MonthlyPayment(monthlyPayment);
        this.category = new PlanCategory(category);
    }

    public Long getId() {
        return id;
    }

    public String getPlanId() {
        return planId.getId();
    }

    public String getName() {
        if (Objects.isNull(name))
            return null;
        return name.getName();
    }

    public String getNetworkTech() {
        if (Objects.isNull(networkTech))
            return null;
        return networkTech.getName();
    }

    public Integer getMonthlyPayment() {
        if (Objects.isNull(monthlyPayment))
            return null;
        return monthlyPayment.getMonthlyPayment();
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

    public List<DataPolicy> getDataPolicies() {
        return dataPolicies.values().stream().toList();
    }

    public void addUnLimitDataPolicy(DataPolicyUnitPeriod unitPeriod) {
        DataPolicy dataPolicy = new DataPolicy(this, null);
        dataPolicies.put(unitPeriod, dataPolicy);
    }

    public void addLimitDataPolicy(DataPolicyUnitPeriod unitPeriod, int dataQuantity) {
        DataPolicy dataPolicy = new DataPolicy(this, dataQuantity);
        dataPolicies.put(unitPeriod, dataPolicy);
    }

    public void addPolicyDetail(DataPolicyUnitPeriod unitPeriod, int dataBoundary, Long speedLimit, Integer dataUnit, Double cost, Long maxCost) {
        DataPolicy dataPolicy = dataPolicies.get(unitPeriod);
        dataPolicy.addPolicyDetail(dataBoundary, speedLimit, dataUnit, cost, maxCost);
    }

    public List<PremiumService> getPremiumServices() {
        return premiumServices;
    }

    public void addPremiumService(PremiumService premiumService) {
        if (!premiumServices.contains(premiumService))
            premiumServices.add(premiumService);
    }

    public List<MediaService> getMediaServices() {
        return mediaServices;
    }

    public void addMediaService(MediaService mediaService) {
        if (!mediaServices.contains(mediaService))
            mediaServices.add(mediaService);
    }


}
