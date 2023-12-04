package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class ColorNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"흰", "빨간색"})
    @DisplayName("색상명은 한 글자 이상의 문자열로 생성할 수 있다.")
    void newColorName_1(String name) {
        assertThatCode(() -> new ColorName(name)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("텍스트가 없는 문자열이나 널로 색상명을 생성하면 예외가 발생한다.")
    void newColorName_2(String name) {
        assertThatThrownBy(() -> new ColorName(name)).isInstanceOf(IllegalArgumentException.class);
    }

}