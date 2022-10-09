package com.qwe910205.plusdotcom.plan.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MonthlyPaymentTest {

    @Test
    void 요금제의_월정액은_음수일_수_없다() {
        // given
        int incorrectPayment = -1;
        int correctPayment = 0;

        // when then
        assertThatThrownBy(() -> new MonthlyPayment(incorrectPayment)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new MonthlyPayment(correctPayment).getMonthlyPayment()).isEqualTo(correctPayment);
    }

}