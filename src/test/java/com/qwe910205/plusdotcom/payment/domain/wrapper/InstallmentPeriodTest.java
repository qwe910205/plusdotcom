package com.qwe910205.plusdotcom.payment.domain.wrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class InstallmentPeriodTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 49, 100})
    @DisplayName("할부 기간이 1 ~ 48개월이 아니라면 예외가 발생한다.")
    void createInstallmentPeriodWithInvalidValue(int period) {
        assertThatThrownBy(() -> new InstallmentPeriod(period)).isInstanceOf(IllegalArgumentException.class);
    }
}