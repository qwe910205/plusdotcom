package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PhoneModelNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"Galaxy Z flip 4", "g"})
    @DisplayName("스마트폰 모델명을 한 글자 이상의 문자열로 생성할 수 있다.")
    void newPhoneModelName_1(String name) {
        assertThatCode(() -> new PhoneModelName(name)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("스마트폰 모델명을 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void newPhoneModelName_2(String name) {
        assertThatThrownBy(() -> new PhoneModelName(name)).isInstanceOf(IllegalArgumentException.class);
    }

}