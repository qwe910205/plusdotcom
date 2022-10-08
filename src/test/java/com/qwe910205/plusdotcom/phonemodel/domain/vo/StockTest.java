package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void 재고는_음수일_수_없다() {
        // given
        int stock = -1;

        // when // then
        Assertions.assertThatThrownBy(() -> new Stock(stock)).isInstanceOf(IllegalArgumentException.class);
    }

}