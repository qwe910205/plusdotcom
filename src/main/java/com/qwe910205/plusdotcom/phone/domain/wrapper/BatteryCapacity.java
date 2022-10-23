package com.qwe910205.plusdotcom.phone.domain.wrapper;

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
    private int value;

    public BatteryCapacity(int value) {
        checkIntegrity(value);
        this.value = value;
    }

    private void checkIntegrity(int value) {
        if (value < 0)
            throw new IllegalArgumentException("배터리의 용량은 음수일 수 없습니다.");
    }
}
