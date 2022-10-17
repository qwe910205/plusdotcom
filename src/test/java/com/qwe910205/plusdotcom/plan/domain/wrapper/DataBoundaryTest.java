package com.qwe910205.plusdotcom.plan.domain.wrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataBoundaryTest {

    @Test
    @DisplayName("데이터 경곗값을 음수로 생성하면 예외가 발생한다.")
    void createDataBoundaryWithNegative() {
        int boundary = -1;

        Assertions.assertThatThrownBy(() -> new DataBoundary(boundary)).isInstanceOf(IllegalArgumentException.class);
    }
}