package com.qwe910205.plusdotcom.plan.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class PlanIdTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("요금제 아이디를 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void createPlanIdWithNegative(String id) {
        assertThatThrownBy(() -> new PlanId(id)).isInstanceOf(IllegalArgumentException.class);
    }

}