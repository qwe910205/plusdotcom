package com.qwe910205.plusdotcom.payment.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class NoneDiscountApplier implements DiscountApplier {

    @Override
    public boolean canApplyDiscount(PaymentSpecification paymentSpecification) {
        DiscountType discountType = paymentSpecification.getDiscountType();
        return discountType.equals(DiscountType.NONE);
    }

    @Override
    public void applyDiscount(PaymentSpecification paymentSpecification) {
        if (!canApplyDiscount(paymentSpecification))
            throw new IllegalArgumentException("해당 명세서는 할인을 적용해야 합니다.");
    }
}
