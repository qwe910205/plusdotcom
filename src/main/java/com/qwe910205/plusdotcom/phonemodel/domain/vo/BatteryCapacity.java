package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BatteryCapacity {

    /** 단위는 mAh */
    private Integer batteryCapacity;

    public BatteryCapacity(int batteryCapacity) {
        if (batteryCapacity < 0)
            throw new IllegalArgumentException("배터리의 용량은 음수일 수 없습니다.");
        this.batteryCapacity = batteryCapacity;
    }
}
