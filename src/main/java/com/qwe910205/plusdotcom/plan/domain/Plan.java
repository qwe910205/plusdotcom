package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.plan.domain.vo.MonthlyPayment;
import com.qwe910205.plusdotcom.plan.domain.vo.PlanDescription;
import com.qwe910205.plusdotcom.plan.domain.vo.PlanId;
import com.qwe910205.plusdotcom.plan.domain.vo.PlanName;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = {"id"})
@Entity
public class Plan {

    @EmbeddedId
    private PlanId id;

    @AttributeOverride(name = "name", column = @Column(nullable = false, unique = true))
    @Embedded
    private PlanName name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private NetworkTech networkTech;

    @AttributeOverride(name = "monthlyPayment", column = @Column(nullable = false))
    @Embedded
    private MonthlyPayment monthlyPayment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private PlanCategory category;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(nullable = false)
    private DataPolicy dataPolicy;

    @Embedded
    private PlanDescription description;

    @ManyToMany
    private List<PremiumService> premiumServices = new ArrayList<>();

    @ManyToMany
    private List<MediaService> mediaServices = new ArrayList<>();
}
