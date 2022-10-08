package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTechNameTest {

    @Test
    void 통신_기술명은_한_글자_이상이다() {
        // given
        String name1 = " ";
        String name2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> new NetworkTechName(name1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new NetworkTechName(name2)).isInstanceOf(IllegalArgumentException.class);
    }

}