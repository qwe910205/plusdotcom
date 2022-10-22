package com.qwe910205.plusdotcom.payment.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CommitmentPeriodTest {

    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 25, 100})
    @DisplayName("약정 기간이 0 ~ 24개월이 아니라면 예외가 발생한다.")
    void createCommitmentPeriodWithInvalidValue(int period) {
        assertThatThrownBy(() -> new CommitmentPeriod(period)).isInstanceOf(IllegalArgumentException.class);
    }

}