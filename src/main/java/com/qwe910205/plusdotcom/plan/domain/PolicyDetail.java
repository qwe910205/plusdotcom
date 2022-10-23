package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.plan.domain.wrapper.DataBoundary;
import com.qwe910205.plusdotcom.plan.domain.wrapper.DataSpeed;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@EqualsAndHashCode(of = {"dataPolicy", "dataBoundary"})
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

    @AttributeOverride(name = "value", column = @Column(name = "DATA_BOUNDARY", nullable = false))
    @Embedded
    private DataBoundary dataBoundary;

    @AttributeOverride(name = "value", column = @Column(name = "SPEED_LIMIT"))
    @Embedded
    private DataSpeed speedLimit;

    @Embedded
    private DataExcessChargePolicy excessChargePolicy;

    public PolicyDetail(DataPolicy dataPolicy, int dataBoundary, Long speedLimit) {
        this.dataPolicy = dataPolicy;
        this.dataBoundary = new DataBoundary(dataBoundary);
        if (Objects.nonNull(speedLimit))
            this.speedLimit = new DataSpeed(speedLimit);
    }

    public void setExcessChargePolicy(DataExcessChargePolicy excessChargePolicy) {
        this.excessChargePolicy = excessChargePolicy;
    }

    @Override
    public int compareTo(PolicyDetail o) {
        return this.dataBoundary.getValue() - o.dataBoundary.getValue();
    }
}
