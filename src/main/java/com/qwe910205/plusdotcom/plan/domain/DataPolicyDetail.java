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
public class DataPolicyDetail implements Comparable<DataPolicyDetail> {

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

    @AttributeOverrides({
            @AttributeOverride(name = "dataUnit", column = @Column(name = "DATA_UNIT", nullable = true)),
            @AttributeOverride(name = "cost", column = @Column(name = "COST", nullable = true)),
            @AttributeOverride(name = "maximumCharge", column = @Column(name = "MAXIMUM_CHARGE", nullable = true))
    })
    @Embedded
    private ChargePolicyAboutExcessDataUsage chargePolicy;

    public DataPolicyDetail(DataPolicy dataPolicy, int dataBoundary, Long speedLimit) {
        this.dataPolicy = dataPolicy;
        this.dataBoundary = new DataBoundary(dataBoundary);
        if (Objects.nonNull(speedLimit))
            this.speedLimit = new DataSpeed(speedLimit);
    }

    public void setChargePolicyAboutExcessDataUsage(ChargePolicyAboutExcessDataUsage chargePolicy) {
        this.chargePolicy = chargePolicy;
    }

    public int getDataBoundary() {
        return dataBoundary.getValue();
    }

    public long getChargeAbout(long dataUsage) {
        if (!hasChargeAboutExcessDataUsage() || dataUsage <= 0)
            return 0;
        return chargePolicy.getChargeAbout(dataUsage);
    }

    private boolean hasChargeAboutExcessDataUsage() {
        return Objects.nonNull(chargePolicy);
    }

    @Override
    public int compareTo(DataPolicyDetail o) {
        return this.dataBoundary.getValue() - o.dataBoundary.getValue();
    }
}
