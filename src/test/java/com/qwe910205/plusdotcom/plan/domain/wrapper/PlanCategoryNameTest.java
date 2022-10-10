package com.qwe910205.plusdotcom.plan.domain.wrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PlanCategoryNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("요금제 카테고리명을 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void createPlanCategoryNameWithoutText(String name) {
        Assertions.assertThatThrownBy(() -> new PlanCategoryName(name)).isInstanceOf(IllegalArgumentException.class);
    }

}