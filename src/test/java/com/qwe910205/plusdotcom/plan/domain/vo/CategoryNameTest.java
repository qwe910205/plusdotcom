package com.qwe910205.plusdotcom.plan.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryNameTest {

    @Test
    void 카테고리명은_한_글자_이상입니다() {
        // given
        String name1 = " ";
        String name2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> new CategoryName(name1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new CategoryName(name2)).isInstanceOf(IllegalArgumentException.class);
    }

}