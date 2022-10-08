package com.qwe910205.plusdotcom.plan.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DataExcessChargePolicy {

    /** 단위는 KB */
    private Long dataUnit;

    private Double cost;

    private Integer maxCost;

    public DataExcessChargePolicy(long dataUnit, double cost) {
        if (dataUnit <= 0)
            throw new IllegalArgumentException("단위 데이터는 양수여야 합니다.");
        if (cost <= 0)
            throw new IllegalArgumentException("최대 비용이 무한대일 때 부과 비용은 양수여야 합니다.");
        this.dataUnit = dataUnit;
        this.cost = cost;
    }

    public DataExcessChargePolicy(long dataUnit, double cost, int maxCost) {
        if (dataUnit <= 0)
            throw new IllegalArgumentException("단위 데이터는 양수여야 합니다.");
        if (cost < 0)
            throw new IllegalArgumentException("비용은 음수일 수 없습니다.");
        if (maxCost < 0)
            throw new IllegalArgumentException("최대 비용은 음수일 수 없습니다.");
        this.dataUnit = dataUnit;
        this.cost = cost;
        this.maxCost = maxCost;
    }
}
