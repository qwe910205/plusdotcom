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

    private Integer price;

    public Price(int price) {
        checkIntegrity(price);
        this.price = price;
    }

    public Price plus(Price amount) {
        int newPrice = price + amount.getPrice();
        checkIntegrity(newPrice);
        return new Price(newPrice);
    }

    public Price minus(Price amount) {
        int newPrice = Math.max(0, price - amount.getPrice());
        return new Price(newPrice);
    }

    public Price multiply(int value) {
        int newPrice = price * value;
        checkIntegrity(newPrice);
        return new Price(newPrice);
    }

    public Price divide(int value) {
        if (value == 0) return this;
        int newPrice = price / value;
        return new Price(newPrice);
    }

    private void checkIntegrity(int price) {
        if (price < 0)
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
    }
}
