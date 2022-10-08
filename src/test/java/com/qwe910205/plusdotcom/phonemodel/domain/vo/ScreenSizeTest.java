package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScreenSizeTest {

    @Test
    void 화면_크기는_음수일_수_없다() {
        // given
        double screenSize = -1;

        // when // then
        Assertions.assertThatThrownBy(() -> new ScreenSize(screenSize)).isInstanceOf(IllegalArgumentException.class);
    }

}