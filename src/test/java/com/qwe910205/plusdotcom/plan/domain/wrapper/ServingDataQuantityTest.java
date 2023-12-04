package com.qwe910205.plusdotcom.plan.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ServingDataQuantityTest {

    @Test
    @DisplayName("기본 제공 데이터량은 음수일 수 없습니다")
    void newServingDataQuantity() {
        int servingDataQuantity = -1;

        assertThatThrownBy(() -> new ServingDataQuantity(servingDataQuantity)).isInstanceOf(IllegalArgumentException.class);
    }

}