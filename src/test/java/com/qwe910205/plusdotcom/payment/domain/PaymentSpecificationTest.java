package com.qwe910205.plusdotcom.payment.domain;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelId;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PaymentSpecificationTest {

    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    PlanRepository planRepository;

    @Test
    @DisplayName("지불 금액 명세서를 생성하면 명세서의 스마트폰 영역의 정상가는 할부원금과 같다.")
    void createPaymentSpecification_1() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        PaymentSpecification.PhoneField phoneField = paymentSpecification.getPhoneField();
        int normalPrice = phoneField.getNormalPrice();
        assertThat(normalPrice).isEqualTo(phoneField.getInstallmentPrinciple());
    }

    @Test
    @DisplayName("지불 금액 명세서를 생성하면 명세서의 스마트폰 영역의 공시지원금과 추가지원금은 0이다.")
    void createPaymentSpecification_2() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        PaymentSpecification.PhoneField phoneField = paymentSpecification.getPhoneField();
        int publiclySubsidy = phoneField.getPubliclySubsidy();
        int additionalSubsidy = phoneField.getAdditionalSubsidy();
        assertThat(publiclySubsidy).isEqualTo(additionalSubsidy).isZero();
    }

    @Test
    @DisplayName("지불 금액 명세서를 생성하면 명세서의 요금제 영역의 기본 통신 요금은 지불해야 할 통신 요금과 같다.")
    void createPaymentSpecification_3() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        PaymentSpecification.PlanField planField = paymentSpecification.getPlanField();
        int normalFee = planField.getNormalFee();
        int fee = planField.getFee();
        assertThat(normalFee).isEqualTo(fee);
    }

    @Test
    @DisplayName("지불 금액 명세서를 생성하면 명세서의 요금제 영역의 할인 금액은 0이다.")
    void createPaymentSpecification_4() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        PaymentSpecification.PlanField planField = paymentSpecification.getPlanField();
        int commitmentDiscountAmount = planField.getCommitmentDiscountAmount();
        assertThat(commitmentDiscountAmount).isZero();
    }

    @Test
    @DisplayName("지불 금액 명세서에 할부 기간이 0이라면 할부 금액은 할부원금과 할부수수료를 더한 값이다.")
    void createPaymentSpecification_5() {
        PaymentSpecification paymentSpecification = getPaymentSpecificationWithoutInstallment();

        int monthlyInstallment = paymentSpecification.getPhoneField().getMonthlyInstallment();
        int installmentPrinciple = paymentSpecification.getPhoneField().getInstallmentPrinciple();
        int installmentFee = paymentSpecification.getPhoneField().getInstallmentFee();
        assertThat(monthlyInstallment).isEqualTo(installmentPrinciple + installmentFee);
    }

    @Transactional
    @Test
    @DisplayName("지불 금액 명세서에 공시지원금을 적용하면 스마트폰의 할부 금액과 그 외의 정보가 그에 맞게 변할 수 있다.")
    void applyPubliclySubsidy() {
        PaymentSpecification paymentSpecification = getPaymentSpecificationWithoutInstallment();
        int installmentPrinciple = paymentSpecification.getPhoneField().getInstallmentPrinciple();

        paymentSpecification.applyPubliclySubsidy();

        int changedPubliclySubsidy = paymentSpecification.getPhoneField().getPubliclySubsidy();
        int changedAdditionalSubsidy = paymentSpecification.getPhoneField().getAdditionalSubsidy();
        assertThat(changedPubliclySubsidy / 100 * 15).isEqualTo(changedAdditionalSubsidy);
        int changedInstallmentPrinciple = paymentSpecification.getPhoneField().getInstallmentPrinciple();
        assertThat(installmentPrinciple).isEqualTo(changedInstallmentPrinciple + changedPubliclySubsidy + changedAdditionalSubsidy);
        int monthlyInstallment = paymentSpecification.getPhoneField().getMonthlyInstallment();
        assertThat(monthlyInstallment).isEqualTo(changedInstallmentPrinciple + paymentSpecification.getPhoneField().getInstallmentFee());
    }

    @Transactional
    @Test
    @DisplayName("지불 금액 명세서에 공시지원금을 적용하면 추가지원금은 공시지원금의 15퍼센트가 된다.")
    void applyPubliclySubsidy_1() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        paymentSpecification.applyPubliclySubsidy();

        int publiclySubsidy = paymentSpecification.getPhoneField().getPubliclySubsidy();
        int additionalSubsidy = paymentSpecification.getPhoneField().getAdditionalSubsidy();
        assertThat(additionalSubsidy).isEqualTo(publiclySubsidy / 100 * 15);
    }

    @Transactional
    @Test
    @DisplayName("지불 금액 명세서에 공시지원금을 적용하면 원래 할부원금은 바뀐 할부원금, 바뀐 공시지원금, 바뀐 추가지원금을 모두 더한 값이다.")
    void applyPubliclySubsidy_2() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();
        int installmentPrinciple = paymentSpecification.getPhoneField().getInstallmentPrinciple();

        paymentSpecification.applyPubliclySubsidy();

        int publiclySubsidy = paymentSpecification.getPhoneField().getPubliclySubsidy();
        int additionalSubsidy = paymentSpecification.getPhoneField().getAdditionalSubsidy();
        int changedInstallmentPrinciple = paymentSpecification.getPhoneField().getInstallmentPrinciple();
        assertThat(installmentPrinciple).isEqualTo(changedInstallmentPrinciple + publiclySubsidy + additionalSubsidy);
    }

    @Transactional
    @Test
    @DisplayName("지불 금액 명세서에 공시지원금을 적용하면 할부 금액은 할부원금과 할부수수료를 더한 값에서 할부 기간을 나눈 값이다.")
    void applyPubliclySubsidy_3() {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        paymentSpecification.applyPubliclySubsidy();

        int monthlyInstallment = paymentSpecification.getPhoneField().getMonthlyInstallment();
        int installmentPrinciple = paymentSpecification.getPhoneField().getInstallmentPrinciple();
        int installmentFee = paymentSpecification.getPhoneField().getInstallmentFee();
        int installmentPeriod = paymentSpecification.getPhoneField().getInstallmentPeriod();
        assertThat(monthlyInstallment).isEqualTo((installmentPrinciple + installmentFee) / installmentPeriod);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10000})
    @DisplayName("지불 금액 명세서에 약정 할인 금액을 바꾸면 그만큼 요금제의 비용이 차감된다.")
    void setCommitmentDiscountAmount(int amount) {
        PaymentSpecification paymentSpecification = getPaymentSpecification();
        int fee = paymentSpecification.getPlanField().getFee();

        paymentSpecification.setCommitmentDiscountAmount(amount);

        int changedFee = paymentSpecification.getPlanField().getFee();
        assertThat(changedFee).isEqualTo(fee - amount);
    }

    @ParameterizedTest
    @ValueSource(ints = {1000000, 12301923})
    @DisplayName("요금제의 비용보다 더 큰 값을 차감해도 음수로 내려가진 않는다.")
    void setCommitmentDiscountAmountWithGreaterThanFee(int amount) {
        PaymentSpecification paymentSpecification = getPaymentSpecification();

        paymentSpecification.setCommitmentDiscountAmount(amount);

        int changedFee = paymentSpecification.getPlanField().getFee();
        assertThat(changedFee).isZero();
    }

    private PaymentSpecification getPaymentSpecification() {
        PhoneModel phoneModel = phoneRepository.findByPhoneModelId(new PhoneModelId("SM-F721N512")).orElseThrow();
        Plan plan = planRepository.findByPlanId(new PlanId("LPZ0000433")).orElseThrow();
        int installmentPeriod = 24;
        DiscountType discountType = DiscountType.NONE;
        return new PaymentSpecification(phoneModel, plan, installmentPeriod, discountType);
    }

    private PaymentSpecification getPaymentSpecificationWithoutInstallment() {
        PhoneModel phoneModel = phoneRepository.findByPhoneModelId(new PhoneModelId("SM-F721N512")).orElseThrow();
        Plan plan = planRepository.findByPlanId(new PlanId("LPZ0000433")).orElseThrow();
        int installmentPeriod = 0;
        DiscountType discountType = DiscountType.NONE;
        return new PaymentSpecification(phoneModel, plan, installmentPeriod, discountType);
    }

}