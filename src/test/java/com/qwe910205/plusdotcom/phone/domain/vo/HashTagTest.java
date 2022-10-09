package com.qwe910205.plusdotcom.phone.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HashTagTest {

    @Test
    void 해시태그는_한_글자_이상이다() {
        // given
        String incorrectHashTag1 = " ";
        String incorrectHashTag2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> new HashTag(incorrectHashTag1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new HashTag(incorrectHashTag2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 해시태그를_생성하면_맨_앞에_샵이_붙는다() {
        // given
        String string = "hashTag";

        // when
        HashTag hashTag = new HashTag(string);

        // then
        Assertions.assertThat(hashTag.getHashTag()).isEqualTo("#" + string);
    }

}