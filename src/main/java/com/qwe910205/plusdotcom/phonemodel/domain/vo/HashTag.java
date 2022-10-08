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
public class HashTag {

    private String hashTag;

    public HashTag(String hashTag) {
        if (!StringUtils.hasText(hashTag))
            throw new IllegalArgumentException("해시 태그는 한 글자 이상이어야 합니다.");
        this.hashTag = "#" + hashTag;
    }
}