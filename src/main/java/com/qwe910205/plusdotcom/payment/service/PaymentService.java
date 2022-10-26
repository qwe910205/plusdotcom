package com.qwe910205.plusdotcom.payment.service;

import com.qwe910205.plusdotcom.payment.domain.DiscountType;
import com.qwe910205.plusdotcom.payment.domain.PaymentSpecification;
import com.qwe910205.plusdotcom.payment.domain.factory.PaymentSpecificationFactory;
import com.qwe910205.plusdotcom.payment.service.dto.PaymentSpecificationDto;
import com.qwe910205.plusdotcom.payment.service.dto.PaymentSpecificationListDto;
import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelId;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PaymentService {

    private final PhoneRepository phoneRepository;
    private final PlanRepository planRepository;

    public PaymentSpecificationDto getPaymentSpecification(String modelId, String planId, Integer installmentPeriod, String discountTypeName) {
        PhoneModel phoneModel = phoneRepository.findByPhoneModelId(new PhoneModelId(modelId))
                .orElseThrow(() -> new NoSuchElementException("스마트폰 모델 아이디가 " + modelId + "인 스마트폰 모델은 존재하지 않습니다."));
        Plan plan = planRepository.findByPlanId(new PlanId(planId))
                .orElseThrow(() -> new NoSuchElementException("요금제 아이디가 " + planId + "인 요금제는 존재하지 않습니다."));
        DiscountType discountType = resolveDiscountTypeName(discountTypeName);

        PaymentSpecification paymentSpecification = PaymentSpecificationFactory.createAndApplyDiscount(phoneModel, plan, installmentPeriod, discountType);

        return PaymentSpecificationDto.create(paymentSpecification);
    }

    public PaymentSpecificationListDto getPaymentSpecifications(String planId, Integer installmentPeriod, String discountTypeName) {
        Plan plan = planRepository.findByPlanId(new PlanId(planId))
                .orElseThrow(() -> new NoSuchElementException("요금제 아이디가 " + planId + "인 요금제는 존재하지 않습니다."));
        String networkTech = plan.getNetworkTech();
        List<PhoneModel> phoneModels = phoneRepository.findByNetworkTech(new NetworkTech(networkTech));
        DiscountType discountType = resolveDiscountTypeName(discountTypeName);

        return new PaymentSpecificationListDto(
                phoneModels.stream()
                .map(phoneModel -> PaymentSpecificationFactory.createAndApplyDiscount(phoneModel, plan, installmentPeriod, discountType))
                .map(PaymentSpecificationDto::create)
                .toList()
        );
    }

    private DiscountType resolveDiscountTypeName(String discountTypeName) {
        return Arrays.stream(DiscountType.values()).filter(type -> type.getName().equals(discountTypeName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할인 방식입니다."));
    }
}
