package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvenienceFunctionNameTest {

    @Test
    void 편의기능명은_한_글자_이상이다() {
        // given
        String incorrectName1 = " ";
        String incorrectName2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> new ConvenienceFunctionName(incorrectName1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new ConvenienceFunctionName(incorrectName2)).isInstanceOf(IllegalArgumentException.class);
    }

}