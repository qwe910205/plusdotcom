package com.qwe910205.plusdotcom.plan.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MonthlyPaymentTest {

    @Test
    @DisplayName("요금제의 월정액을 음수로 생성하면 예외가 발생한다.")
    void createMonthlyPaymentWithNegative() {
        int payment = -1;

        assertThatThrownBy(() -> new MonthlyPayment(payment)).isInstanceOf(IllegalArgumentException.class);
    }

}