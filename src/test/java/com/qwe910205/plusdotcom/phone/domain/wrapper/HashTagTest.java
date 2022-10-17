package com.qwe910205.plusdotcom.phone.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class HashTagTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("해시태그를 텍스트가 없는 문자열이나 널로 생성하면 예외가 발생한다.")
    void createHashTagWithoutText(String hashTag) {
        assertThatThrownBy(() -> new HashTag(hashTag)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("맨 앞에 #이 붙은 문자열로 해시태그를 생성할 수 있다.")
    void createHashTagWithStringStartWithSharp() {
        String string = "#hashTag";

        HashTag hashTag = new HashTag(string);

        assertThat(hashTag.getHashTag()).isEqualTo(string);
    }

    @Test
    @DisplayName("맨 앞에 #이 붙지 않은 문자열로 해시태그를 생성하면 자동으로 #이 붙는다.")
    void createHashTagWithStringNotStartWithSharp() {
        String string = "hashTag";

        HashTag hashTag = new HashTag(string);

        assertThat(hashTag.getHashTag()).isEqualTo("#" + string);
    }
}