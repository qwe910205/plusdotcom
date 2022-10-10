package com.qwe910205.plusdotcom.plan.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DataSpeedTest {

    @Test
    @DisplayName("데이터 속도를 음수로 생성하면 예외가 발생한다.")
    void createDataSpeedWithNegative() {
        long speed = -1;

        assertThatThrownBy(() -> new DataSpeed(speed)).isInstanceOf(IllegalArgumentException.class);
    }

}