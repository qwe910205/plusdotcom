package com.qwe910205.plusdotcom.phonemodel.domain.vo;


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

    private String name;

    public PhoneModelName(String name) {
        if (!StringUtils.hasText(name))
            throw new IllegalArgumentException("스마트폰 모델의 이름은 한 글자 이상이어야 합니다.");
        this.name = name;
    }
}
