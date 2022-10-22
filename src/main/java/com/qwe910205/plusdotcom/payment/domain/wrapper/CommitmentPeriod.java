package com.qwe910205.plusdotcom.payment.domain.wrapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CommitmentPeriod {

    /** 단위는 개월 */
    private final Integer period;

    public CommitmentPeriod(int period) {
        if (!(0 <= period && period <= 24))
            throw new IllegalArgumentException("약정 기간은 0 ~ 24개월이어야 합니다.");
        this.period = period;
    }
}
