package com.qwe910205.plusdotcom.payment.domain.factory;

import com.qwe910205.plusdotcom.payment.domain.*;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DiscountApplierFactoryTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("지불 금액 명세서에 맞는 할인 적용자를 받을 수 있다.")
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
        PhoneModel phoneModel = createPhoneModel();
        Plan plan = createPlan();
        int installmentPeriod = 24;
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

}