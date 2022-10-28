package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class ImageSourceTest {

    @ParameterizedTest
    @ValueSource(strings = {"https://www.naver.com", "http://google.com"})
    @DisplayName("이미지 주소를 전형적인 url 형식으로 생성할 수 있다.")
    void newImageSource_1(String url) {
        assertThatCode(() -> new ImageSource(url)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ftp://www.naver.com", "htp://www.naver.com"})
    @DisplayName("이미지 주소의 프로토콜을 http 또는 https가 아닌 프로토콜로 생성하면 예외가 발생한다.")
    void newImageSource_2(String url) {
        assertThatThrownBy(() -> new ImageSource(url)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"www.naver.com", "https://naver.com", "naver.com"})
    @DisplayName("이미지 주소는 프로토콜과 www 없이 생성할 수 있다.")
    void newImageSource_3(String url) {
        assertThatCode(() -> new ImageSource(url)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://com.com", "https://www.naver.com.com.com.com.com.com"})
    @DisplayName("이미지 주소를 2단계 이상의 도메인 주소로 생성할 수 있다.")
    void newImageSource_4(String url) {
        assertThatCode(() -> new ImageSource(url)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://com", "http://", "com"})
    @DisplayName("이미지 주소의 도메인 주소가 1단계이하이면 예외가 발생한다.")
    void newImageSource_5(String url) {
        assertThatThrownBy(() -> new ImageSource(url)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("-_가 포함된 이미지 주소를 생성할 수 있다.")
    void newImageSource_6() {
        String url = "https://image.lguplus.com/static/pc-contents/images/prdv/20220812-020701-465-vFiTeAxV.jpg";

        assertThatCode(() -> new ImageSource(url)).doesNotThrowAnyException();
    }

}