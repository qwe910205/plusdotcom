package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PhoneModelIdTest {

    @ParameterizedTest
    @ValueSource(strings = {"1", "SM-0123"})
    @DisplayName("스마트폰 모델 아이디를 한 글자 이상의 문자열로 생성할 수 있다.")
    void createPhoneModelId(String id) {
        assertThatCode(() -> new PhoneModelId(id)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("스마트폰 모델 아이디를 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void createPhoneModelIdWithoutText(String id) {
        assertThatThrownBy(() -> new PhoneModelId(id)).isInstanceOf(IllegalArgumentException.class);
    }

}