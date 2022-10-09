package com.qwe910205.plusdotcom.plan.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataBoundaryTest {

    @Test
    void 데이터_경곗값은_음수일_수_없다() {
        // given
        int incorrectValue = -1;
        int correctValue1 = 0;
        int correctValue2 = Integer.MAX_VALUE;

        // when then
        Assertions.assertThatThrownBy(() -> new DataBoundary(incorrectValue)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThat(new DataBoundary(correctValue1).getBoundary()).isEqualTo(correctValue1);
        Assertions.assertThat(new DataBoundary(correctValue2).getBoundary()).isEqualTo(correctValue2);
    }
}