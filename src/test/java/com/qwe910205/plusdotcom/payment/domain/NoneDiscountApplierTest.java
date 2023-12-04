package com.qwe910205.plusdotcom.payment.domain;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class NoneDiscountApplierTest {

    @Autowired NoneDiscountApplier discountApplier;

    @ParameterizedTest
    @EnumSource(value = DiscountType.class, names = {"NONE"}, mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("할인 방식이 없는게 아닌 지불 금액 명세서에 할인 없음을 적용할 수 없다.")
    void canApplyDiscount(DiscountType discountType) {
        PaymentSpecification paymentSpecification = new PaymentSpecification(createPhoneModel(), createPlan(), 24, discountType);

        boolean canApply = discountApplier.canApplyDiscount(paymentSpecification);

        assertThat(canApply).isFalse();
    }

    @Test
    @DisplayName("할인 방식이 없는 지불 금액 명세서에 할인 없음을 적용하면 금액 변화가 없다.")
    void applyDiscount() {
        PaymentSpecification paymentSpecification = new PaymentSpecification(createPhoneModel(), createPlan(), 24, DiscountType.NONE);
        Integer totalMonthlyPayment = paymentSpecification.getTotalMonthlyPayment();

        discountApplier.applyDiscount(paymentSpecification);

        Integer afterTotalMonthlyPayment = paymentSpecification.getTotalMonthlyPayment();
        assertThat(totalMonthlyPayment).isEqualTo(afterTotalMonthlyPayment);
    }

    private PhoneModel createPhoneModel() {
        return PhoneModel.builder()
                .modelId("SM-F721N-MK")
                .name("갤럭시 Z Flip 4 메종키츠네 에디션")
                .manufacturer("SAMSUNG")
                .networkTech("5G")
                .price(1386000)
                .build();
    }

    private Plan createPlan() {
        return Plan.builder()
                .planId("Z202205252")
                .name("5G 프리미어 플러스")
                .networkTech("5G")
                .basicMonthlyCharge(105000)
                .category("데이터 무제한")
                .build();
    }

}