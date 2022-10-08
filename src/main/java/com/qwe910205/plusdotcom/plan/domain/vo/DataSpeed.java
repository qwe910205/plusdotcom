package com.qwe910205.plusdotcom.plan.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DataSpeed {

    /** 단위는 Kbps */
    private Long speed;

    public DataSpeed(Long speed) {
        if (speed < 0)
            throw new IllegalArgumentException("데이터 속도는 음수일 수 없습니다.");
        this.speed = speed;
    }
}
