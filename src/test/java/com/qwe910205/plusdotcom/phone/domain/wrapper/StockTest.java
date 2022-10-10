package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    @Test
    @DisplayName("재고를 음수로 생성하면 예외가 발생한다.")
    void createStockWithNegative() {
        int stock = -1;

        Assertions.assertThatThrownBy(() -> new Stock(stock)).isInstanceOf(IllegalArgumentException.class);
    }

}