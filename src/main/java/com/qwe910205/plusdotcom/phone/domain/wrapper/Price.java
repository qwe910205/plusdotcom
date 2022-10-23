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
public class Price {

    private int value;

    public Price(int value) {
        checkIntegrity(value);
        this.value = value;
    }

    public Price plus(Price amount) {
        int newPrice = value + amount.getValue();
        checkIntegrity(newPrice);
        return new Price(newPrice);
    }

    public Price minus(Price amount) {
        int newPrice = Math.max(0, value - amount.getValue());
        return new Price(newPrice);
    }

    public Price multiply(int value) {
        int newPrice = this.value * value;
        checkIntegrity(newPrice);
        return new Price(newPrice);
    }

    public Price divide(int value) {
        if (value == 0) return this;
        int newPrice = this.value / value;
        return new Price(newPrice);
    }

    private void checkIntegrity(int price) {
        if (price < 0)
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
    }
}
