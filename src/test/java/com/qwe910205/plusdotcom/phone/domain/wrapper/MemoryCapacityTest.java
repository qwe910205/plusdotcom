package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class MemoryCapacityTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("메모리의 ram 용량과 rom 용량을 Integer 타입의 양수나 0으로 생성할 수 있다.")
    void createMemoryCapacity(int ram, int rom) {
        assertThatCode(() -> new MemoryCapacity(ram, rom)).doesNotThrowAnyException();
    }

    private static Stream<Arguments> createMemoryCapacity() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("메모리의 용량을 음수로 생성하면 예외가 발생한다.")
    void createMemoryCapacityNegative(int ram, int rom) {
        assertThatThrownBy(() -> new MemoryCapacity(ram, rom)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> createMemoryCapacityNegative() {
        return Stream.of(
                Arguments.of(-1, Integer.MAX_VALUE),
                Arguments.of(Integer.MAX_VALUE, -1)
        );
    }
}
