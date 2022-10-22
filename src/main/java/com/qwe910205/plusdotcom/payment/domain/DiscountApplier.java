package com.qwe910205.plusdotcom.payment.domain;

public interface DiscountApplier {

    boolean canApplyDiscount(PaymentSpecification paymentSpecification);

    void applyDiscount(PaymentSpecification paymentSpecification);
}
