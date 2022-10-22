package com.qwe910205.plusdotcom.payment.domain.factory;

import com.qwe910205.plusdotcom.payment.domain.*;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelId;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DiscountApplierFactoryTest {

    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    PlanRepository planRepository;

    @Transactional
    @ParameterizedTest
    @MethodSource
    @DisplayName("요금 명세서에 맞는 할인 적용자를 받을 수 있다.")
    void getDiscountApplier(DiscountType discountType, Class<DiscountApplier> discountApplierClass) {
        PaymentSpecification paymentSpecification = getPaymentSpecification(discountType);

        DiscountApplier discountApplier = DiscountApplierFactory.getDiscountApplier(paymentSpecification);

        assertThat(discountApplier.getClass()).isEqualTo(discountApplierClass);
    }

    private static Stream<Arguments> getDiscountApplier() {
        return Stream.of(
            Arguments.of(DiscountType.NONE, NoneDiscountApplier.class),
            Arguments.of(DiscountType.PUBLICLY_SUBSIDY, PubliclySubsidyDiscountApplier.class),
            Arguments.of(DiscountType.COMMITMENT_12MONTH, CommitmentDiscountApplier.class),
            Arguments.of(DiscountType.COMMITMENT_24MONTH, CommitmentDiscountApplier.class)
        );
    }

    private PaymentSpecification getPaymentSpecification(DiscountType discountType) {
        PhoneModel phoneModel = phoneRepository.findByPhoneModelId(new PhoneModelId("SM-F721N512")).orElseThrow();
        Plan plan = planRepository.findByPlanId(new PlanId("LPZ0000433")).orElseThrow();
        int installmentPeriod = 24;
        return new PaymentSpecification(phoneModel, plan, installmentPeriod, discountType);
    }

}