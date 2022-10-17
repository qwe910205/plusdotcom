package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.plan.domain.wrapper.ServingDataQuantity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DataPolicy {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Plan plan;

    @Embedded
    private ServingDataQuantity servingDataQuantity;

    @SortNatural
    @OneToMany(mappedBy = "dataPolicy",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PolicyDetail> policyDetails = new ArrayList<>();

    public DataPolicy(Plan plan, Integer dataQuantity) {
        this.plan = plan;
        if (Objects.nonNull(dataQuantity))
            this.servingDataQuantity = new ServingDataQuantity(dataQuantity);
    }

    public void addPolicyDetail(int dataBoundary, Long speedLimit, Integer dataUnit, Double cost, Long maxCost) {
        PolicyDetail policyDetail = new PolicyDetail(this, dataBoundary, speedLimit);
        if (Objects.nonNull(dataUnit))
            policyDetail.setExcessChargePolicy(new DataExcessChargePolicy(dataUnit, cost, maxCost));
        this.policyDetails.add(policyDetail);
    }
}
