package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class ManufacturerNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"SAMSUNG", "s"})
    @DisplayName("제조사명을 한 글자 이상의 문자열로 생성할 수 있다.")
    void newManufacturerName_1(String name) {
        assertThatCode(() -> new ManufacturerName(name)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("제조사명을 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void newManufacturerName_2(String name) {
        assertThatThrownBy(() -> new ManufacturerName(name)).isInstanceOf(IllegalArgumentException.class);
    }

}