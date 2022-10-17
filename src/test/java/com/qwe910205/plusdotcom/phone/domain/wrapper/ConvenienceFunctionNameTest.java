package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class ConvenienceFunctionNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"뺷", "지문인식", "안면 인식"})
    @DisplayName("편의기능명은 한 글자 이상의 문자열로 생성할 수 있다.")
    void createFunctionName(String name) {
        assertThatCode(() -> new ConvenienceFunctionName(name)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("편의기능명을 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void createFunctionNameWithoutText(String name) {
        assertThatThrownBy(() -> new ConvenienceFunctionName(name)).isInstanceOf(IllegalArgumentException.class);
    }

}