package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.plan.domain.vo.ServingDataQuantity;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DataPolicy {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private DataPolicyUnitPeriod unitPeriod;

    @Embedded
    private ServingDataQuantity servingDataQuantity;

    @SortNatural
    @OneToMany(mappedBy = "dataPolicy",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PolicyDetail> policyDetails = new ArrayList<>();
}
