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
public class Stock {

    private Integer stock;

    public Stock(int stock) {
        if (stock < 0)
            throw new IllegalArgumentException("재고는 음수일 수 없습니다.");
        this.stock = stock;
    }
}
