package com.qwe910205.plusdotcom.plan.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ServiceNameTest {

    @Test
    void 서비스명은_한_글자_이상이다() {
        // given
        String incorrectName1 = "  ";
        String incorrectName2 = null;
        String correctName = "a";

        // when then
        assertThatThrownBy(() -> new ServiceName(incorrectName1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ServiceName(incorrectName2)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new ServiceName(correctName).getName()).isEqualTo(correctName);
    }

}