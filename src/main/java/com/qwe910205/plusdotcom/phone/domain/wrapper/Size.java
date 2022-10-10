package com.qwe910205.plusdotcom.phone.domain.wrapper;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Size {

    /** 단위는 mm */
    private Double height;

    /** 단위는 mm */
    private Double width;

    /** 단위는 mm */
    private Double thickness;

    public Size(double height, double width, double thickness) {
        if (height < 0 || width < 0 || thickness < 0)
            throw new IllegalArgumentException("스마트폰의 크기 요소는 음수일 수 없습니다.");
        this.height = height;
        this.width = width;
        this.thickness = thickness;
    }
}
