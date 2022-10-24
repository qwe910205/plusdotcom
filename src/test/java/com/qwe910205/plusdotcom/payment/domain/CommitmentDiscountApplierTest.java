package com.qwe910205.plusdotcom.payment.domain;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CommitmentDiscountApplierTest {
    @Autowired
    CommitmentDiscountApplier commitmentDiscountApplier;

    @Test
    @DisplayName("할인 방식이 약정 할인인 지불 금액 명세서에 약정 할인을 적용할 수 있다.")
    void applyCommitmentDiscount() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();
        int fee = paymentSpecification.getPlanField().getFee();

        commitmentDiscountApplier.applyDiscount(paymentSpecification);

        assertThat(paymentSpecification.getPlanField().getFee()).isLessThan(fee);
    }

    private PaymentSpecification getPaymentSpecification() {
        PhoneModel phoneModel = createPhoneModel();
        Plan plan = createPlan();
        int installmentPeriod = 24;
        DiscountType discountType = DiscountType.COMMITMENT_24MONTH;
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