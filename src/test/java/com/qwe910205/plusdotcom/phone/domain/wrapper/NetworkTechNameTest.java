package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class NetworkTechNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"5G", "5", "g"})
    @DisplayName("통신기술명을 한 글자 이상의 문자열로 생성할 수 있다.")
    void newNetworkTechName_1(String name) {
        assertThatCode(() -> new NetworkTechName(name)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("통신기술명을 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void newNetworkTechName_2(String name) {
        assertThatThrownBy(() -> new NetworkTechName(name)).isInstanceOf(IllegalArgumentException.class);
    }

}