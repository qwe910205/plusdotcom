package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class MoneyTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1000, Integer.MAX_VALUE})
    @DisplayName("가격을 0이나 자연수로 생성할 수 있다.")
    void createPrice(int price) {
        assertThatCode(() -> new Money(price)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("가격을 음수로 생성하면 예외가 발생한다.")
    void createPriceWithNegative() {
        int price = -1;

        assertThatThrownBy(() -> new Money(price)).isInstanceOf(IllegalArgumentException.class);
    }

}