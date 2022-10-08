package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.plan.domain.vo.DataBoundary;
import com.qwe910205.plusdotcom.plan.domain.vo.DataExcessChargePolicy;
import com.qwe910205.plusdotcom.plan.domain.vo.DataSpeed;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(
        name = "POLICY_BOUNDARY_UNIQUE",
        columnNames = {"DATA_POLICY_ID", "DATA_BOUNDARY"}
))
@Entity
public class PolicyDetail implements Comparable<PolicyDetail> {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private DataPolicy dataPolicy;

    @AttributeOverride(name = "boundary", column = @Column(name = "DATA_BOUNDARY", nullable = false))
    @Embedded
    private DataBoundary dataBoundary;

    @AttributeOverride(name = "speed", column = @Column(name = "SPEED_LIMIT"))
    @Embedded
    private DataSpeed speedLimit;

    @AttributeOverrides({
            @AttributeOverride(name = "dataUnit", column = @Column(nullable = false)),
            @AttributeOverride(name = "cost", column = @Column(nullable = false))
    })
    @Embedded
    private DataExcessChargePolicy excessChargePolicy;

    @Override
    public int compareTo(PolicyDetail o) {
        return this.dataBoundary.getBoundary() - o.dataBoundary.getBoundary();
    }
}
