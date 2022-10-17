package com.qwe910205.plusdotcom.phone.domain.wrapper;

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
public class ConvenienceFunctionName implements Serializable {

    private String name;

    public ConvenienceFunctionName(String name) {
        if (!StringUtils.hasText(name))
            throw new IllegalArgumentException("편의기능명은 한 글자 이상이어야 합니다.");
        this.name = name;
    }
}