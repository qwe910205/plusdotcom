package com.qwe910205.plusdotcom.payment.domain;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelId;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CommitmentDiscountApplierTest {

    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    PlanRepository planRepository;
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
        PhoneModel phoneModel = phoneRepository.findByPhoneModelId(new PhoneModelId("SM-F721N512")).orElseThrow();
        Plan plan = planRepository.findByPlanId(new PlanId("LPZ0000433")).orElseThrow();
        int installmentPeriod = 24;
        DiscountType discountType = DiscountType.COMMITMENT_24MONTH;
        return new PaymentSpecification(phoneModel, plan, installmentPeriod, discountType);
    }

}