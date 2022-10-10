package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class BatteryCapacityTest {

    @ParameterizedTest
    @ValueSource(ints = {0, Integer.MAX_VALUE, 1000, 123456789})
    @DisplayName("0이나 자연수로 배터리 용량을 생성할 수 있다.")
    void createBatteryCapacityWithPositive(int capacity) {
        assertThatCode(() -> new BatteryCapacity(capacity)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1})
    @DisplayName("배터리 용량을 음수로 생성하면 예외가 발생한다.")
    void createBatterCapacityWithNegative(int negative) {
        assertThatThrownBy(() -> new BatteryCapacity(negative)).isInstanceOf(IllegalArgumentException.class);
    }

}