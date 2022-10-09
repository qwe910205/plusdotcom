package com.qwe910205.plusdotcom.plan.domain.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DataSpeedTest {

    @Test
    void 데이터_속도는_음수일_수_없다() {
        // given
        long incorrectSpeed = -1;
        long correctSpeed = 0;

        // when then
        assertThatThrownBy(() -> new DataSpeed(incorrectSpeed)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new DataSpeed(correctSpeed).getSpeed()).isEqualTo(correctSpeed);
    }

}