package com.qwe910205.plusdotcom.plan.domain.wrapper;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DataSpeed {

    /** 단위는 Kbps */
    private long value;

    public DataSpeed(long value) {
        checkIntegrity(value);
        this.value = value;
    }

    private void checkIntegrity(long value) {
        if (value < 0)
            throw new IllegalArgumentException("데이터 속도는 음수일 수 없습니다.");
    }
}
