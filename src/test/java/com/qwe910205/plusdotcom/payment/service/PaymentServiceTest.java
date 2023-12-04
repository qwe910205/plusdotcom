package com.qwe910205.plusdotcom.payment.service;

import com.qwe910205.plusdotcom.payment.service.dto.PaymentSpecificationDto;
import com.qwe910205.plusdotcom.payment.service.dto.PaymentSpecificationListDto;
import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {

    @Autowired PaymentService paymentService;
    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    PlanRepository planRepository;

    @Test
    @DisplayName("스마트폰 모델 아이디, 요금제 아이디, 할부 기간, 할인 방식을 선택하면 그에 맞는 지불 금액 명세서를 조회할 수 있다.")
    void getPaymentSpecification() {
        String modelId = "SM-F721N512";
        String planId = "LPZ0000433";
        int installmentPeriod = 24;
        String discountTypeName = "공시지원금";

        PaymentSpecificationDto paymentSpecification = paymentService.getPaymentSpecification(modelId, planId, installmentPeriod, discountTypeName);

        assertThat(paymentSpecification.phoneField().modelId()).isEqualTo(modelId);
        assertThat(paymentSpecification.planField().planId()).isEqualTo(planId);
        assertThat(paymentSpecification.phoneField().installmentPeriod()).isEqualTo(installmentPeriod);
        assertThat(paymentSpecification.discountType()).isEqualTo(discountTypeName);
        System.out.println(paymentSpecification);
    }

    @Test
    @DisplayName("요금제 아이디, 할부 기간, 할인 방식을 선택하면 모든 스마트폰 모델에 대해 그에 맞는 지불 금액 명세서를 조회할 수 있다.")
    void getPaymentSpecifications() {
        String planId = "LPZ0000433";
        int installmentPeriod = 24;
        String discountTypeName = "공시지원금";

        PaymentSpecificationListDto paymentSpecifications = paymentService.getPaymentSpecifications(planId, installmentPeriod, discountTypeName);

        String networkTech = planRepository.findByPlanId(new PlanId(planId)).orElseThrow().getNetworkTech();
        int count = phoneRepository.findByNetworkTech(new NetworkTech(networkTech)).size();
        assertThat(paymentSpecifications.paymentSpecifications().size()).isEqualTo(count);
        paymentSpecifications.paymentSpecifications().forEach(System.out::println);
    }

    @Test
    @DisplayName("정해진 할인 방식 이외의 할인 방식 이름을 입력하면 예외가 발생한다.")
    void getPaymentSpecificationWithInvalidDiscountTypeName() {
        String modelId = "SM-F721N512";
        String planId = "LPZ0000433";
        int installmentPeriod = 24;
        String discountTypeName = "공시지원";

        assertThatThrownBy(() -> paymentService.getPaymentSpecification(modelId, planId, installmentPeriod, discountTypeName))
                .isInstanceOf(IllegalArgumentException.class);
    }
}