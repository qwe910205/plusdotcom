package com.qwe910205.plusdotcom.payment.domain;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PubliclySubsidyDiscountApplierTest {

    @Autowired
    PubliclySubsidyDiscountApplier applier;

    @Test
    @DisplayName("지불 금액 명세서의 할인 방식이 공시지원금이라면 공시지원금 할인을 적용할 수 있다.")
    void applyPubliclySubsidyDiscount() {
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
                .id("SM-F721N-MK")
                .name("갤럭시 Z Flip 4 메종키츠네 에디션")
                .manufacturer("SAMSUNG")
                .networkTech("5G")
                .price(1386000)
                .build();
    }

    private Plan createPlan() {
        return Plan.builder()
                .id("Z202205252")
                .name("5G 프리미어 플러스")
                .networkTech("5G")
                .monthlyPayment(105000)
                .category("데이터 무제한")
                .build();
    }

}