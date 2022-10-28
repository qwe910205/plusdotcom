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
public class Money {

    private int value;

    public Money(int value) {
        checkIntegrity(value);
        this.value = value;
    }

    public Money plus(Money amount) {
        int newPrice = value + amount.getValue();
        checkIntegrity(newPrice);
        return new Money(newPrice);
    }

    public Money minus(Money amount) {
        int newPrice = Math.max(0, value - amount.getValue());
        return new Money(newPrice);
    }

    public Money multiply(int value) {
        int newPrice = this.value * value;
        checkIntegrity(newPrice);
        return new Money(newPrice);
    }

    public Money divide(int value) {
        if (value == 0) return this;
        int newPrice = (int) Math.round((double) this.value / value);
        return new Money(newPrice);
    }

    private void checkIntegrity(int price) {
        if (price < 0)
            throw new RuntimeException("가격은 음수일 수 없습니다.");
    }
}
