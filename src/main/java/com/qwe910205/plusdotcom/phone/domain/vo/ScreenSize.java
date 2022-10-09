package com.qwe910205.plusdotcom.phone.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ScreenSize {

    /** 단위는 inch */
    private Double screenSize;

    public ScreenSize(double screenSize) {
        if (screenSize < 0)
            throw new IllegalArgumentException("화면 크기는 음수일 수 없습니다.");
        this.screenSize = screenSize;
    }
}
