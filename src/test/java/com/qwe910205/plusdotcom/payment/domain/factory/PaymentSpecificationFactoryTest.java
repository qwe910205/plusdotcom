package com.qwe910205.plusdotcom.payment.domain.factory;

import com.qwe910205.plusdotcom.payment.domain.DiscountType;
import com.qwe910205.plusdotcom.payment.domain.PaymentSpecification;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PaymentSpecificationFactoryTest {

    @ParameterizedTest
    @EnumSource(DiscountType.class)
    @DisplayName("요금제 카테고리가 만약 다이렉트라면 할인 방식은 무조건 NONE이다.")
    void createAndApplyDiscount(DiscountType discountType) {
        PhoneModel phoneModel = createPhoneModel();
        Plan plan = createPlan();
        int installmentPeriod = 24;

        PaymentSpecification paymentSpecification = PaymentSpecificationFactory.createAndApplyDiscount(phoneModel, plan, installmentPeriod, discountType);

        assertThat(paymentSpecification.getDiscountType()).isEqualTo(DiscountType.NONE);
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
                .planId("LPZ0000595")
                .name("5G 다이렉트 34")
                .networkTech("5G")
                .basicMonthlyCharge(34000)
                .category("다이렉트")
                .build();
    }

}