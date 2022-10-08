package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void 가격은_음수일_수_없다() {
        // given
        int price = -1;

        // when // then
        Assertions.assertThatThrownBy(() -> new Price(price)).isInstanceOf(IllegalArgumentException.class);
    }

}