package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class SizeTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("스마트폰의 크기의 요소를 음수로 생성하면 예외가 발생한다.")
    void createSizeWithNegative(double height, double width, double thickness) {
        assertThatThrownBy(() -> new Size(height, width, thickness)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> createSizeWithNegative() {
        return Stream.of(
            Arguments.of(-1, Integer.MAX_VALUE, Integer.MAX_VALUE),
            Arguments.of(Integer.MAX_VALUE, -1, Integer.MAX_VALUE),
            Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, -1)
        );
    }

}