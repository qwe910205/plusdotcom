package com.qwe910205.plusdotcom.payment.domain;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CommitmentDiscountApplierTest {
    @Autowired
    CommitmentDiscountApplier commitmentDiscountApplier;

    @ParameterizedTest
    @EnumSource(value = DiscountType.class, names = {"COMMITMENT_24MONTH", "COMMITMENT_12MONTH"}, mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("선택약정 할인 방식 이외의 할인 방식의 지불 금액 명세서에는 약정 할인을 적용할 수 없다.")
    void canApplyDiscount(DiscountType discountType) {
        PaymentSpecification paymentSpecification = new PaymentSpecification(createPhoneModel(), createPlan(), 24, discountType);

        boolean canApply = commitmentDiscountApplier.canApplyDiscount(paymentSpecification);

        assertThat(canApply).isFalse();
    }

    @ParameterizedTest
    @EnumSource(value = DiscountType.class, names = {"COMMITMENT_24MONTH", "COMMITMENT_12MONTH"})
    @DisplayName("할인 방식이 약정 할인인 지불 금액 명세서에 약정 할인을 적용할 수 있다.")
    void applyDiscount(DiscountType discountType) {
        PhoneModel phoneModel = createPhoneModel();
        Plan plan = createPlan();
        PaymentSpecification paymentSpecification = new PaymentSpecification(phoneModel, plan, 24, discountType);
        Integer basicMonthlyCharge = plan.getBasicMonthlyCharge();

        commitmentDiscountApplier.applyDiscount(paymentSpecification);

        assertThat(paymentSpecification.getPlanField().getMonthlyCharge()).isLessThan(basicMonthlyCharge);
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