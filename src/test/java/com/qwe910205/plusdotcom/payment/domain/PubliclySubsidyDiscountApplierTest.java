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
class PubliclySubsidyDiscountApplierTest {

    @Autowired
    PubliclySubsidyDiscountApplier applier;

    @ParameterizedTest
    @EnumSource(value = DiscountType.class, names = {"PUBLICLY_SUBSIDY"}, mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("지불 금액 명세서의 할인 방식이 공시지원금이 아니라면 공시지원금 할인을 적용할 수 없다.")
    void canApplyDiscount(DiscountType discountType) {
        PaymentSpecification paymentSpecification = new PaymentSpecification(createPhoneModel(), createPlan(), 24, discountType);

        boolean canApplyDiscount = applier.canApplyDiscount(paymentSpecification);

        assertThat(canApplyDiscount).isFalse();
    }


    @Test
    @DisplayName("지불 금액 명세서의 할인 방식이 공시지원금이라면 공시지원금 할인을 적용할 수 있다.")
    void applyDiscount() {
        PaymentSpecification paymentSpecification = getPaymentSpecificationThatHasPubliclySubsidy();
        PaymentSpecification.PhoneField phoneField = paymentSpecification.getPhoneField();
        int monthlyInstallment = phoneField.getMonthlyInstallment();

        applier.applyDiscount(paymentSpecification);

        assertThat(paymentSpecification.getPhoneField().getMonthlyInstallment()).isLessThan(monthlyInstallment);
    }

    private PaymentSpecification getPaymentSpecificationThatHasPubliclySubsidy() {
        PhoneModel phoneModel = createPhoneModel();
        Plan plan = createPlan();
        phoneModel.putPubliclySubsidy(plan, 50000);
        int installmentPeriod = 24;
        DiscountType discountType = DiscountType.PUBLICLY_SUBSIDY;
        return new PaymentSpecification(phoneModel, plan, installmentPeriod, discountType);
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