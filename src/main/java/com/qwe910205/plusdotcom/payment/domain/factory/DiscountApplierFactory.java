package com.qwe910205.plusdotcom.payment.domain.factory;

import com.qwe910205.plusdotcom.common.utility.BeanUtils;
import com.qwe910205.plusdotcom.payment.domain.DiscountApplier;
import com.qwe910205.plusdotcom.payment.domain.PaymentSpecification;

import java.util.Collection;

public class DiscountApplierFactory {

    public static DiscountApplier getDiscountApplier(PaymentSpecification paymentSpecification) {
        Collection<DiscountApplier> discountAppliers = BeanUtils.getBeansOfType(DiscountApplier.class);

        return discountAppliers.stream()
                .filter(applier -> applier.canApplyDiscount(paymentSpecification))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 명세서에 적용할 수 있는 할인 적용자가 없습니다.")
                );
    }
}
