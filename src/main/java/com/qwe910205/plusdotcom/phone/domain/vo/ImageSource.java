package com.qwe910205.plusdotcom.phone.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ImageSource {

    private String url;

    public ImageSource(String url) {
        if (!Pattern.matches("^((http|https)://)?(www.)?([a-zA-Z0-9]+)\\.[a-z]+([a-zA-Z0-9.?#-_]+)?", url))
            throw new IllegalArgumentException("이미지 주소를 url 형식으로 입력 바랍니다.");
        this.url = url;
    }
}
