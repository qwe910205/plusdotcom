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

import static org.assertj.core.api.Assertions.*;

@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=none", "spring.datasource.url=jdbc:mysql://localhost:3306/uplusdotcom"})
@SpringBootTest
class PaymentSpecificationTest {

    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    PlanRepository planRepository;

    @Test
    @DisplayName("요금 명세서를 생성하면 명세서의 스마트폰 영역의 정상가는 할부원금과 같다.")
    void createPaymentSpecification_1() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        PaymentSpecification.PhoneField phoneField = paymentSpecification.getPhoneField();
        int normalPrice = phoneField.getNormalPrice();
        assertThat(normalPrice).isEqualTo(phoneField.getInstallmentPrinciple());
    }

    @Test
    @DisplayName("요금 명세서를 생성하면 명세서의 스마트폰 영역의 공시지원금과 추가지원금은 0이다.")
    void createPaymentSpecification_2() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        PaymentSpecification.PhoneField phoneField = paymentSpecification.getPhoneField();
        int publiclySubsidy = phoneField.getPubliclySubsidy();
        int additionalSubsidy = phoneField.getAdditionalSubsidy();
        assertThat(publiclySubsidy).isEqualTo(additionalSubsidy).isZero();
    }

    @Test
    @DisplayName("요금 명세서를 생성하면 명세서의 요금제 영역의 기본 통신 요금은 지불해야 할 통신 요금과 같다.")
    void createPaymentSpecification_3() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        PaymentSpecification.PlanField planField = paymentSpecification.getPlanField();
        int normalFee = planField.getNormalFee();
        int fee = planField.getFee();
        assertThat(normalFee).isEqualTo(fee);
    }

    @Test
    @DisplayName("요금 명세서를 생성하면 명세서의 요금제 영역의 할인 금액은 0이다.")
    void createPaymentSpecification_4() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        PaymentSpecification.PlanField planField = paymentSpecification.getPlanField();
        int commitmentDiscountAmount = planField.getCommitmentDiscountAmount();
        assertThat(commitmentDiscountAmount).isZero();
    }

    private PaymentSpecification getPaymentSpecification() {
        PhoneModel phoneModel = phoneRepository.findByPhoneModelId(new PhoneModelId("SM-F721N512")).orElseThrow();
        Plan plan = planRepository.findByPlanId(new PlanId("LPZ0000433")).orElseThrow();
        int installmentPeriod = 24;
        DiscountType discountType = DiscountType.NONE;
        return new PaymentSpecification(phoneModel, plan, installmentPeriod, discountType);
    }

}