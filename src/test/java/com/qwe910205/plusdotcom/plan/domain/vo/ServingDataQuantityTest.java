package com.qwe910205.plusdotcom.plan.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ServingDataQuantityTest {

    @Test
    @DisplayName("기본 제공 데이터량은 음수일 수 없습니다")
    void data_is_not_negative() {
        // given
        int incorrectValue = -1;
        int correctValue = 0;

        // when then
        assertThatThrownBy(() -> new ServingDataQuantity(incorrectValue)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new ServingDataQuantity(correctValue).getQuantity()).isEqualTo(correctValue);
    }

}