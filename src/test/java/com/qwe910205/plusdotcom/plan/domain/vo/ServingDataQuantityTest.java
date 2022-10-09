package com.qwe910205.plusdotcom.plan.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ServingDataQuantityTest {

    @Test
    void 기본_제공_데이터량은_음수일_수_없습니다() {
        // given
        int incorrectValue = -1;
        int correctValue = 0;

        // when then
        assertThatThrownBy(() -> new ServingDataQuantity(incorrectValue)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new ServingDataQuantity(correctValue).getQuantity()).isEqualTo(correctValue);
    }

}