package com.qwe910205.plusdotcom.plan.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanCategoryNameTest {

    @Test
    void 요금제_카테고리명은_한_글자_이상이다() {
        // given
        String name1 = "  ";
        String name2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> new PlanCategoryName(name1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new PlanCategoryName(name2)).isInstanceOf(IllegalArgumentException.class);
    }

}