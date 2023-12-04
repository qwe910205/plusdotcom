package com.qwe910205.plusdotcom.phone.domain.wrapper;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PhoneModelName {

    private String value;

    public PhoneModelName(String value) {
        checkIntegrity(value);
        this.value = value;
    }

    private void checkIntegrity(String value) {
        if (!StringUtils.hasText(value))
            throw new IllegalArgumentException("스마트폰 모델의 이름은 한 글자 이상이어야 합니다.");
    }
}
