package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SizeTest {

    @Test
    void 스마트폰_높이는_음수일_수_없다() {
        // given
        double height = -1;
        double width = 1;
        double thickness = 1;

        // when // then
        Assertions.assertThatThrownBy(() -> new Size(height, width, thickness)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 스마트폰_너비는_음수일_수_없다() {
        // given
        double height = 1;
        double width = -1;
        double thickness = 1;

        // when // then
        Assertions.assertThatThrownBy(() -> new Size(height, width, thickness)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 스마트폰_두께는_음수일_수_없다() {
        // given
        double height = 1;
        double width = 1;
        double thickness = -1;

        // when // then
        Assertions.assertThatThrownBy(() -> new Size(height, width, thickness)).isInstanceOf(IllegalArgumentException.class);
    }

}