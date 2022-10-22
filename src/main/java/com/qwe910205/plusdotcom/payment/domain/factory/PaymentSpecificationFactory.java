package com.qwe910205.plusdotcom.payment.domain.factory;

import com.qwe910205.plusdotcom.payment.domain.DiscountApplier;
import com.qwe910205.plusdotcom.payment.domain.DiscountType;
import com.qwe910205.plusdotcom.payment.domain.PaymentSpecification;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.plan.domain.Plan;

public class PaymentSpecificationFactory {

    public static PaymentSpecification create(PhoneModel phoneModel, Plan plan, int installmentPeriod, DiscountType discountType) {
        if (plan.getCategory().equals("다이렉트"))
            discountType = DiscountType.NONE;

        PaymentSpecification paymentSpecification = new PaymentSpecification(phoneModel, plan, installmentPeriod, discountType);
        DiscountApplier discountApplier = DiscountApplierFactory.getDiscountApplier(paymentSpecification);
        discountApplier.applyDiscount(paymentSpecification);

        return paymentSpecification;
    }
}
