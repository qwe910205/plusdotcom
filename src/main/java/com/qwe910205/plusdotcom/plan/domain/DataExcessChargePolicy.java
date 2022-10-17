package com.qwe910205.plusdotcom.plan.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DataExcessChargePolicy {

    /** 단위는 KB */
    private Integer dataUnit;

    private Double cost;

    private Long maxCost;

    public DataExcessChargePolicy(int dataUnit, double cost) {
        this(dataUnit, cost, null);
    }

    public DataExcessChargePolicy(int dataUnit, double cost, Long maxCost) {
        if (dataUnit <= 0)
            throw new IllegalArgumentException("단위 데이터는 양수여야 합니다.");
        if (cost < 0)
            throw new IllegalArgumentException("단위 당 비용은 음수일 수 없습니다.");
        if (Objects.nonNull(maxCost) && maxCost < 0)
            throw new IllegalArgumentException("최대 비용은 음수일 수 없습니다.");
        this.dataUnit = dataUnit;
        this.cost = cost;
        this.maxCost = maxCost;
    }

    public boolean hasCostLimit() {
        return maxCost != null;
    }
}
