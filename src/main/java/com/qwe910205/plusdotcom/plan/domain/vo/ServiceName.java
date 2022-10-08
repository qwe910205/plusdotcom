package com.qwe910205.plusdotcom.plan.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ServiceName implements Serializable {

    private String name;

    public ServiceName(String name) {
        if (!StringUtils.hasText(name))
            throw new IllegalArgumentException("서비스명은 한 글자 이상이어야 합니다.");
        this.name = name;
    }
}
