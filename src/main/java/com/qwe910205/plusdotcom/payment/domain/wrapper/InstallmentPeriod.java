package com.qwe910205.plusdotcom.payment.domain.wrapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class InstallmentPeriod {

    /** 단위는 개월 */
    private final Integer period;

    public InstallmentPeriod(int period) {
        checkIntegrity(period);
        this.period = period;
    }

    private void checkIntegrity(int period) {
        if (!(0 <= period && period <= 48))
            throw new IllegalArgumentException("할부 기간은 0 ~ 48개월 이어야 합니다.");
    }
}
