package com.qwe910205.plusdotcom.plan.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ChargePolicyAboutExcessDataUsage {

    /** 단위는 KB */
    private int dataUnit;

    private double cost;

    private Integer maximumCharge;

    public ChargePolicyAboutExcessDataUsage(int dataUnit, double cost) {
        this(dataUnit, cost, null);
    }

    public ChargePolicyAboutExcessDataUsage(int dataUnit, double cost, Integer maximumCharge) {
        checkNegative(dataUnit, cost, maximumCharge);
        checkReality(dataUnit, cost);
        this.dataUnit = dataUnit;
        this.cost = cost;
        this.maximumCharge = maximumCharge;
    }

    private void checkNegative(int dataUnit, double cost, Integer maximumCharge) {
        if (dataUnit <= 0)
            throw new IllegalArgumentException("단위 데이터는 양수여야 합니다.");
        if (cost < 0)
            throw new IllegalArgumentException("단위 당 비용은 음수일 수 없습니다.");
        if (!Objects.isNull(maximumCharge) && maximumCharge < 0)
            throw new IllegalArgumentException("최대 비용은 음수일 수 없습니다.");
    }

    private void checkReality(int dataUnit, double cost) {
        if (cost / dataUnit > 1000000)
            throw new IllegalArgumentException("단위 데이터 당 비용이 비현실적입니다.");
    }

    public long getChargeAbout(long dataUsage) {
        checkReality(dataUsage);
        BigDecimal changedDataUsage = changeMBtoKB(dataUsage);
        BigDecimal charge = changedDataUsage.divide(new BigDecimal(dataUnit), RoundingMode.UP).multiply(new BigDecimal(cost));
        if (hasMaximumCharge())
            return Math.min(charge.longValue(), maximumCharge);
        return charge.longValue();
    }

    private void checkReality(long dataUsage) {
        if (dataUsage > 10000000)
            throw new IllegalArgumentException("데이터 사용량이 1TB를 초과할 수 없습니다.");
    }

    private BigDecimal changeMBtoKB(long dataUsage) {
        return new BigDecimal(dataUsage).multiply(new BigDecimal(1000));
    }

    public boolean hasMaximumCharge() {
        return maximumCharge != null;
    }

    public double availableAmountOfDataWhenPayFor(long payment) {
        if (cost == 0) return Double.POSITIVE_INFINITY;
        return dataUnit * Math.floor(payment / cost);
    }
}
