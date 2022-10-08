package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Price {

    private Integer price;

    public Price(int price) {
        if (price < 0)
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        this.price = price;
    }
}
