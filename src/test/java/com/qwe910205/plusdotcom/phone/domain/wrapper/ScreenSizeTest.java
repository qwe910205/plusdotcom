package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class ScreenSizeTest {

    @ParameterizedTest
    @ValueSource(doubles = {0, 1000, Double.MAX_VALUE})
    @DisplayName("화면 크기를 0이나 자연수로 생성할 수 있다.")
    void createScreenSize(double size) {
        assertThatCode(() -> new ScreenSize(size)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("화면 크기를 음수로 생성하면 예외가 발생한다.")
    void createScreenSizeWithNegative() {
        int size = -1;

        assertThatThrownBy(() -> new ScreenSize(size)).isInstanceOf(IllegalArgumentException.class);
    }

}