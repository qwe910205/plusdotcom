package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeightTest {

    @Test
    @DisplayName("무게를 음수로 생성하면 예외가 발생한다.")
    void createWeightWithNegative() {
        int weight = -1;

        Assertions.assertThatThrownBy(() -> new Weight(weight)).isInstanceOf(IllegalArgumentException.class);
    }

}