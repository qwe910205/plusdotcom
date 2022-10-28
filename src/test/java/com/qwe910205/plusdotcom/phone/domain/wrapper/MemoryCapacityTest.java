package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class MemoryCapacityTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, Integer.MAX_VALUE})
    @DisplayName("메모리의 용량을 Integer 타입의 양수나 0으로 생성할 수 있다.")
    void newMemoryCapacity_1(int capacity) {
        assertThatCode(() -> new MemoryCapacity(capacity)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -123123})
    @DisplayName("메모리의 용량을 음수로 생성하면 예외가 발생한다.")
    void newMemoryCapacity_2(int capacity) {
        assertThatThrownBy(() -> new MemoryCapacity(capacity)).isInstanceOf(IllegalArgumentException.class);
    }
}
