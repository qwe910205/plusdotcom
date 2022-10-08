package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BatteryCapacityTest {

    @Test
    void 배터리_용량은_음수일_수_없다() {
        // given
        int capacity = -1;

        // when // then
        Assertions.assertThatThrownBy(() -> new BatteryCapacity(capacity)).isInstanceOf(IllegalArgumentException.class);
    }

}