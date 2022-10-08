package com.qwe910205.plusdotcom.plan.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ServingDataQuantity {

    /** 단위는 MB */
    private Integer quantity;

    public ServingDataQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("기본 데이터 제공량은 음수일 수 없습니다.");
        this.quantity = quantity;
    }
}
