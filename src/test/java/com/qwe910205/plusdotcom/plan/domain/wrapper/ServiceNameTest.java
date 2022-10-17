package com.qwe910205.plusdotcom.plan.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class ServiceNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("서비스명을 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void createServiceNameWithoutText(String name) {
        assertThatThrownBy(() -> new ServiceName(name)).isInstanceOf(IllegalArgumentException.class);
    }

}