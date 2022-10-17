package com.qwe910205.plusdotcom.plan.domain.wrapper;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
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
