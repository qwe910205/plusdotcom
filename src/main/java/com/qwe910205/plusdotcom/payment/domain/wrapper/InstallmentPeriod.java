package com.qwe910205.plusdotcom.payment.domain.wrapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @param value 단위는 개월
 */
public record InstallmentPeriod(int value) {

    public InstallmentPeriod {
        checkIntegrity(value);
    }

    private void checkIntegrity(int period) {
        if (!(0 <= period && period <= 48))
            throw new IllegalArgumentException("할부 기간은 0 ~ 48개월 이어야 합니다.");
    }
}
