package com.qwe910205.plusdotcom.payment.domain.wrapper;

/**
 * @param value 단위는 개월
 */
public record CommitmentPeriod(int value) {

    public CommitmentPeriod {
        checkIntegrity(value);
    }

    private void checkIntegrity(int period) {
        if (!(0 <= period && period <= 24))
            throw new IllegalArgumentException("약정 기간은 0 ~ 24개월이어야 합니다.");
    }
}
