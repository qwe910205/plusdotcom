package com.qwe910205.plusdotcom.payment.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class CommitmentDiscountApplier implements DiscountApplier {

    private final int DISCOUNT_RATE = 25;

    @Override
    public boolean canApplyDiscount(PaymentSpecification paymentSpecification) {
        DiscountType discountType = paymentSpecification.getDiscountType();
        return discountType.equals(DiscountType.COMMITMENT_12MONTH) || discountType.equals(DiscountType.COMMITMENT_24MONTH);
    }

    @Override
    public void applyDiscount(PaymentSpecification paymentSpecification) {
        if (!canApplyDiscount(paymentSpecification))
            throw new IllegalArgumentException("해당 명세서는 약정 할인을 적용할 수 없습니다.");

        PaymentSpecification.PlanField planField = paymentSpecification.getPlanField();
        int basicMonthlyCharge = planField.getBasicMonthlyCharge();
        paymentSpecification.setCommitmentDiscountAmount(basicMonthlyCharge / 100 * DISCOUNT_RATE);
    }
}
