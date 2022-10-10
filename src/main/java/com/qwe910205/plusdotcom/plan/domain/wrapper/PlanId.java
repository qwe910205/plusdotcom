package com.qwe910205.plusdotcom.plan.domain.wrapper;

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
public class PlanId implements Serializable {

    private String id;

    public PlanId(String id) {
        if (!StringUtils.hasText(id))
            throw new IllegalArgumentException("요금제 아이디는 한 글자 이상이어야 합니다.");
        this.id = id;
    }
}
