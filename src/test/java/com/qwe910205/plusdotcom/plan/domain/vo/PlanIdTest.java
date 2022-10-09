package com.qwe910205.plusdotcom.plan.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PlanIdTest {

    @Test
    void 요금제_아이디는_한_글자_이상이다() {
        String incorrectId1 = "  ";
        String incorrectId2 = null;
        String correctId = "a";

        // when then
        assertThatThrownBy(() -> new PlanId(incorrectId1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new PlanId(incorrectId2)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new PlanId(correctId).getId()).isEqualTo(correctId);
    }

}