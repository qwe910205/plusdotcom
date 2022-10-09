package com.qwe910205.plusdotcom.phone.domain.vo;

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
public class ManufacturerName implements Serializable {

    private String name;

    public ManufacturerName(String name) {
        if (!StringUtils.hasText(name))
            throw new IllegalArgumentException("제조사의 이름은 한 글자 이상이어야 합니다.");
        this.name = name;
    }
}
