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
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PubliclySubsidyDiscountApplierTest {

    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    PubliclySubsidyDiscountApplier applier;

    @Transactional
    @Test
    @DisplayName("요금 명세서의 할인 방식이 공시지원금이라면 공시지원금 할인을 적용할 수 있다.")
    void applyPubliclySubsidyDiscount() {
        PaymentSpecification paymentSpecification = getPaymentSpecificationThatHasPubliclySubsidy();
        PaymentSpecification.PhoneField phoneField = paymentSpecification.getPhoneField();
        int monthlyInstallment = phoneField.getMonthlyInstallment();

        applier.applyDiscount(paymentSpecification);

        assertThat(paymentSpecification.getPhoneField().getMonthlyInstallment()).isLessThan(monthlyInstallment);
    }

    private PaymentSpecification getPaymentSpecificationThatHasPubliclySubsidy() {
        PhoneModel phoneModel = phoneRepository.findByPhoneModelId(new PhoneModelId("SM-F721N512")).orElseThrow();
        Plan plan = planRepository.findByPlanId(new PlanId("LPZ0000433")).orElseThrow();
        int installmentPeriod = 24;
        DiscountType discountType = DiscountType.PUBLICLY_SUBSIDY;
        return new PaymentSpecification(phoneModel, plan, installmentPeriod, discountType);
    }

}