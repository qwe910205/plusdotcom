package com.qwe910205.plusdotcom.plan.domain.vo;

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
public class PlanName {

    private String name;

    public PlanName(String name) {
        if (!StringUtils.hasText(name))
            throw new IllegalArgumentException("요금제명은 한 글자 이상이어야 합니다.");
        this.name = name;
    }
}
