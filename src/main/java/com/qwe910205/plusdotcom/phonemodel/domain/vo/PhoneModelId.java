package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PhoneModelId implements Serializable {

    private String id;

    public PhoneModelId(String id) {
        if (!StringUtils.hasText(id))
            throw new IllegalArgumentException("스마트폰 모델 아이디는 한 글자 이상이어야 합니다.");
        this.id = id;
    }
}
