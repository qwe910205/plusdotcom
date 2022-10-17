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
public class MonthlyPayment {

    private Integer monthlyPayment;

    public MonthlyPayment(int monthlyPayment) {
        if (monthlyPayment < 0)
            throw new IllegalArgumentException("요금제의 월정액은 음수일 수 없습니다.");
        this.monthlyPayment = monthlyPayment;
    }
}
