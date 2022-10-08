package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneModelNameTest {

    @Test
    void 스마트폰_모델명은_한_글자_이상이다() {
        // given
        String modelName1 = " ";
        String modelName2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> new PhoneModelName(modelName1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new PhoneModelName(modelName2)).isInstanceOf(IllegalArgumentException.class);
    }

}