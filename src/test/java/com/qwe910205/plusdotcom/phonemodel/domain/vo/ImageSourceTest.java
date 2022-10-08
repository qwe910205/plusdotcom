package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageSourceTest {

    @Test
    void 이미지_주소의_프로토콜은_http_또는_https이다 () {
        // given
        String incorrectUrl1 = "ftp://www.naver.com";
        String incorrectUrl2 = "htp://www.naver.com";
        String correctUrl1 = "http://www.naver.com";
        String correctUrl2 = "https://www.naver.com";

        // when
        ImageSource imageSource1 = new ImageSource(correctUrl1);
        ImageSource imageSource2 = new ImageSource(correctUrl2);

        // then
        Assertions.assertThatThrownBy(() -> new ImageSource(incorrectUrl1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new ImageSource(incorrectUrl2)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThat(imageSource1.getUrl()).isEqualTo(correctUrl1);
        Assertions.assertThat(imageSource2.getUrl()).isEqualTo(correctUrl2);
    }

    @Test
    void 이미지_주소는_프로토콜과_www가_없어도_된다() {
        // given
        String url1 = "www.naver.com";
        String url2 = "https://naver.com";
        String url3 = "naver.com";

        // when
        ImageSource imageSource1 = new ImageSource(url1);
        ImageSource imageSource2 = new ImageSource(url2);
        ImageSource imageSource3 = new ImageSource(url3);

        // then
        Assertions.assertThat(imageSource1.getUrl()).isEqualTo(url1);
        Assertions.assertThat(imageSource2.getUrl()).isEqualTo(url2);
        Assertions.assertThat(imageSource3.getUrl()).isEqualTo(url3);
    }

    @Test
    void 이미지_주소의_도메인_주소는_최소_2단계이다 () {
        // given
        String incorrectUrl1 = "https://com";
        String correctUrl1 = "https://www.naver.com.com.com.com.com.com";

        // when
        ImageSource imageSource = new ImageSource(correctUrl1);

        // then
        Assertions.assertThatThrownBy(() -> new ImageSource(incorrectUrl1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThat(imageSource.getUrl()).isEqualTo(correctUrl1);
    }

}