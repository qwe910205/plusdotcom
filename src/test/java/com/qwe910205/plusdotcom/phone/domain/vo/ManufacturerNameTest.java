package com.qwe910205.plusdotcom.phone.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ManufacturerNameTest {

    @Test
    void 제조사명은_한_글자_이상이다() {
        // given
        String incorrectName1 = " ";
        String incorrectName2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> new ManufacturerName(incorrectName1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new ManufacturerName(incorrectName2)).isInstanceOf(IllegalArgumentException.class);
    }

}