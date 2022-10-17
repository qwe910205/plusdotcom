package com.qwe910205.plusdotcom.plan.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class PlanNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("요금제명을 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void createPlanNameWithoutText(String name) {
        assertThatThrownBy(() -> new PlanName(name)).isInstanceOf(IllegalArgumentException.class);
    }
}