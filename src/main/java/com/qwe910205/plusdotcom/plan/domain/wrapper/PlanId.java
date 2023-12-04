package com.qwe910205.plusdotcom.plan.domain.wrapper;

import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PlanId {

    private String value;

    public PlanId(String value) {
        checkIntegrity(value);
        this.value = value;
    }

    private void checkIntegrity(String value) {
        if (!StringUtils.hasText(value))
            throw new IllegalArgumentException("요금제 아이디는 한 글자 이상이어야 합니다.");
    }

    @Override
    public String toString() {
        return value;
    }
}
