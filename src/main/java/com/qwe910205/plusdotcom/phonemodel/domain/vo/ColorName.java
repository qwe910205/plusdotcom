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
public class ColorName {

    private String name;

    public ColorName(String name) {
        if (!StringUtils.hasText(name))
            throw new IllegalArgumentException("색상명은 한 글자 이상이어야 합니다.");
        this.name = name;
    }
}
