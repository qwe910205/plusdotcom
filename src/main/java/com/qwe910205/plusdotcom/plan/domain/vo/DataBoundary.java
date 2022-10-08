package com.qwe910205.plusdotcom.plan.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DataBoundary {

    /** 단위는 MB */
    private Integer boundary;

    public DataBoundary(int boundary) {
        if (boundary < 0)
            throw new IllegalArgumentException("데이터 경계값은 음수일 수 없습니다.");
        this.boundary = boundary;
    }
}
