package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class ColorCodeTest {

    @ParameterizedTest
    @ValueSource(strings = {"#fff", "#AAAA", "#1d8a9C"})
    @DisplayName("맨 앞에 #이 붙고 [a-fA-F0-9]인 문자가 3~6개인 문자열을 색상코드로 생성할 수 있다.")
    void newColorCode_1(String code) {
        assertThatCode(() -> new ColorCode(code)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"#aaaaag", "aaaaaa", "#1234567"})
    @DisplayName("형식에 맞지 않는 문자열을 색상코드를 생성하면 예외가 발생한다.")
    void newColorCode_2(String code) {
        assertThatThrownBy(() -> new ColorCode(code)).isInstanceOf(IllegalArgumentException.class);
    }
}