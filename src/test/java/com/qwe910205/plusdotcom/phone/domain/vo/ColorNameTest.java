package com.qwe910205.plusdotcom.phone.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ColorNameTest {

    @Test
    void 색상명은_한_글자_이상이다() {
        // given
        String incorrectName1 = " ";
        String incorrectName2 = "";
        String incorrectName3 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> new ColorName(incorrectName1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new ColorName(incorrectName2)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new ColorName(incorrectName3)).isInstanceOf(IllegalArgumentException.class);
    }

}