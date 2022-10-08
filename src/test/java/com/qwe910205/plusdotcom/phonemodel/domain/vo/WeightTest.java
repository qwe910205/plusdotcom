package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeightTest {

    @Test
    void 무게는_음수일_수_없다() {
        // given
        int weight = -1;

        // when // then
        Assertions.assertThatThrownBy(() -> new Weight(weight)).isInstanceOf(IllegalArgumentException.class);
    }

}