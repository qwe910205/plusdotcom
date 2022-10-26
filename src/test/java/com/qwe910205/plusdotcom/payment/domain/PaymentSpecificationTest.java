package com.qwe910205.plusdotcom.payment.domain;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PaymentSpecificationTest {

    @Test
    @DisplayName("통신 기술이 다른 스마트폰 모델과 요금제를 같이 구매할 수 없습니다.")
    void createPaymentSpecificationWithModelAndPlanThatHaveDifferentNetworkTech() {
        PhoneModel phoneModel = createPhoneModel();
        Plan ltePlan = createLTEPlan();

        assertThatThrownBy(() -> new PaymentSpecification(phoneModel, ltePlan, 24, DiscountType.NONE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("지불 금액 명세서를 생성하면 명세서의 스마트폰 영역의 정상가는 할부원금과 같다.")
    void createPaymentSpecification_1() {
        PaymentSpecification paymentSpecification = createPaymentSpecification();

        PaymentSpecification.PhoneField phoneField = paymentSpecification.getPhoneField();
        int normalPrice = phoneField.getNormalPrice();
        assertThat(normalPrice).isEqualTo(phoneField.getInstallmentPrinciple());
    }

    @Test
    @DisplayName("지불 금액 명세서를 생성하면 명세서의 스마트폰 영역의 공시지원금과 추가지원금은 0이다.")
    void createPaymentSpecification_2() {
        PaymentSpecification paymentSpecification = createPaymentSpecification();

        PaymentSpecification.PhoneField phoneField = paymentSpecification.getPhoneField();
        int publiclySubsidy = phoneField.getPubliclySubsidy();
        int additionalSubsidy = phoneField.getAdditionalSubsidy();
        assertThat(publiclySubsidy).isEqualTo(additionalSubsidy).isZero();
    }

    @Test
    @DisplayName("지불 금액 명세서를 생성하면 명세서의 요금제 영역의 기본 통신 요금은 지불해야 할 통신 요금과 같다.")
    void createPaymentSpecification_3() {
        PaymentSpecification paymentSpecification = createPaymentSpecification();

        PaymentSpecification.PlanField planField = paymentSpecification.getPlanField();
        int normalFee = planField.getBasicMonthlyCharge();
        int fee = planField.getMonthlyCharge();
        assertThat(normalFee).isEqualTo(fee);
    }

    @Test
    @DisplayName("지불 금액 명세서를 생성하면 명세서의 요금제 영역의 할인 금액은 0이다.")
    void createPaymentSpecification_4() {
        PaymentSpecification paymentSpecification = createPaymentSpecification();

        PaymentSpecification.PlanField planField = paymentSpecification.getPlanField();
        int commitmentDiscountAmount = planField.getCommitmentDiscountAmount();
        assertThat(commitmentDiscountAmount).isZero();
    }

    @Test
    @DisplayName("지불 금액 명세서에 할부 기간이 0이라면 할부 금액은 할부원금과 할부수수료를 더한 값이다.")
    void createPaymentSpecification_5() {
        PaymentSpecification paymentSpecification = createPaymentSpecificationWithoutInstallment();

        int monthlyInstallment = paymentSpecification.getPhoneField().getMonthlyInstallment();
        int installmentPrinciple = paymentSpecification.getPhoneField().getInstallmentPrinciple();
        int installmentFee = paymentSpecification.getPhoneField().getInstallmentFee();
        assertThat(monthlyInstallment).isEqualTo(installmentPrinciple + installmentFee);
    }

    @Test
    @DisplayName("지불 금액 명세서에 공시지원금을 적용하면 스마트폰의 할부 금액과 그 외의 정보가 그에 맞게 변할 수 있다.")
    void applyPubliclySubsidy() {
        PaymentSpecification paymentSpecification = createPaymentSpecificationWithoutInstallment();
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

    @Test
    @DisplayName("지불 금액 명세서에 공시지원금을 적용하면 추가지원금은 공시지원금의 15퍼센트가 된다.")
    void applyPubliclySubsidy_1() {
        PaymentSpecification paymentSpecification = createPaymentSpecification();

        paymentSpecification.applyPubliclySubsidy();

        int publiclySubsidy = paymentSpecification.getPhoneField().getPubliclySubsidy();
        int additionalSubsidy = paymentSpecification.getPhoneField().getAdditionalSubsidy();
        assertThat(additionalSubsidy).isEqualTo(publiclySubsidy / 100 * 15);
    }

    @Test
    @DisplayName("지불 금액 명세서에 공시지원금을 적용하면 바뀐 할부원금은 원래 할부원금에서 바뀐 공시지원금과 바뀐 추가지원금을 뺀 값이다.")
    void applyPubliclySubsidy_2() {
        PaymentSpecification paymentSpecification = createPaymentSpecification();
        int installmentPrinciple = paymentSpecification.getPhoneField().getInstallmentPrinciple();

        paymentSpecification.applyPubliclySubsidy();

        int publiclySubsidy = paymentSpecification.getPhoneField().getPubliclySubsidy();
        int additionalSubsidy = paymentSpecification.getPhoneField().getAdditionalSubsidy();
        int changedInstallmentPrinciple = paymentSpecification.getPhoneField().getInstallmentPrinciple();
        assertThat(changedInstallmentPrinciple).isEqualTo(installmentPrinciple - publiclySubsidy - additionalSubsidy);
    }

    @Test
    @DisplayName("지불 금액 명세서에 공시지원금을 적용하면 할부 금액은 할부원금과 할부수수료를 더한 값에서 할부 기간을 나눈 값이다.")
    void applyPubliclySubsidy_3() {
        PaymentSpecification paymentSpecification = createPaymentSpecification();

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
        PaymentSpecification paymentSpecification = createPaymentSpecification();
        int fee = paymentSpecification.getPlanField().getMonthlyCharge();

        paymentSpecification.setCommitmentDiscountAmount(amount);

        int changedFee = paymentSpecification.getPlanField().getMonthlyCharge();
        assertThat(changedFee).isEqualTo(fee - amount);
    }

    @ParameterizedTest
    @ValueSource(ints = {1000000, 12301923})
    @DisplayName("요금제의 비용보다 더 큰 값을 차감해도 음수로 내려가진 않는다.")
    void setCommitmentDiscountAmountWithGreaterThanFee(int amount) {
        PaymentSpecification paymentSpecification = createPaymentSpecification();

        paymentSpecification.setCommitmentDiscountAmount(amount);

        int changedFee = paymentSpecification.getPlanField().getMonthlyCharge();
        assertThat(changedFee).isZero();
    }

    private PaymentSpecification createPaymentSpecification() {
        PhoneModel phoneModel = createPhoneModel();
        Plan plan = createPlan();
        phoneModel.putPubliclySubsidy(plan, 50000);
        int installmentPeriod = 24;
        DiscountType discountType = DiscountType.NONE;
        return new PaymentSpecification(phoneModel, plan, installmentPeriod, discountType);
    }

    private PaymentSpecification createPaymentSpecificationWithoutInstallment() {
        PhoneModel phoneModel = createPhoneModel();
        Plan plan = createPlan();
        phoneModel.putPubliclySubsidy(plan, 50000);
        int installmentPeriod = 0;
        DiscountType discountType = DiscountType.NONE;
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
                .basicMonthlyCharge(105000)
                .category("데이터 무제한")
                .build();
    }

    private Plan createLTEPlan() {
        return Plan.builder()
                .id("LPZ0000679")
                .name("LTE 프리미어 에센셜")
                .networkTech("LTE")
                .basicMonthlyCharge(85000)
                .category("데이터 무제한")
                .build();
    }

}