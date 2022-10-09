package com.qwe910205.plusdotcom.phone.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ColorCodeTest {

    @Test
    void 색상코드가_아닌_문자열을_입력할_수_없다() {
        // given
        String incorrectCode1 = "#aaaaag";
        String incorrectCode2 = "aaaaaa";
        String incorrectCode3 = "#1234567";

        // when // then
        Assertions.assertThatThrownBy(() -> new ColorCode(incorrectCode1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new ColorCode(incorrectCode2)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new ColorCode(incorrectCode3)).isInstanceOf(IllegalArgumentException.class);
    }
}