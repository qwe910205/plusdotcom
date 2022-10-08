package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneModelIdTest {

    @Test
    void 스마트폰_모델_아이디는_한_글자_이상이다() {
        // given
        String id1 = " ";
        String id2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> new PhoneModelId(id1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new PhoneModelId(id2)).isInstanceOf(IllegalArgumentException.class);
    }

}