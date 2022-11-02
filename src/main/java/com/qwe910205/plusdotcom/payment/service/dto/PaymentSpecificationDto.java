package com.qwe910205.plusdotcom.payment.service.dto;

import com.qwe910205.plusdotcom.payment.domain.PaymentSpecification;
import lombok.Builder;

@Builder
public record PaymentSpecificationDto(String discountType, PhoneFieldDto phoneField, PlanFieldDto planField, Integer totalMonthlyPayment) {

    public static PaymentSpecificationDto createFrom(PaymentSpecification paymentSpecification) {
        return PaymentSpecificationDto.builder()
                .discountType(paymentSpecification.getDiscountTypeName())
                .phoneField(PhoneFieldDto.createFrom(paymentSpecification.getPhoneField()))
                .planField(PlanFieldDto.createFrom(paymentSpecification.getPlanField()))
                .totalMonthlyPayment(paymentSpecification.getTotalMonthlyPayment())
                .build();
    }

    @Builder
    public record PhoneFieldDto(String modelId, Integer normalPrice, Integer publiclySubsidy, Integer additionalSubsidy,
                                Integer installmentPeriod, Integer installmentPrinciple, Integer installmentFee,
                                Integer monthlyInstallment) {

        public static PhoneFieldDto createFrom(PaymentSpecification.PhoneField phoneField) {
            return PhoneFieldDto.builder()
                    .modelId(phoneField.getModelId())
                    .normalPrice(phoneField.getNormalPrice())
                    .publiclySubsidy(phoneField.getPubliclySubsidy())
                    .additionalSubsidy(phoneField.getAdditionalSubsidy())
                    .installmentPeriod(phoneField.getInstallmentPeriod())
                    .installmentPrinciple(phoneField.getInstallmentPrinciple())
                    .installmentFee(phoneField.getInstallmentFee())
                    .monthlyInstallment(phoneField.getMonthlyInstallment())
                    .build();
        }
    }

    @Builder
    public record PlanFieldDto(String planId, Integer normalFee, Integer commitmentPeriod, Integer commitmentDiscountAmount,
                               Integer fee) {

        public static PlanFieldDto createFrom(PaymentSpecification.PlanField planField) {
            return PlanFieldDto.builder()
                    .planId(planField.getPlanId())
                    .normalFee(planField.getBasicMonthlyCharge())
                    .commitmentPeriod(planField.getCommitmentPeriod())
                    .commitmentDiscountAmount(planField.getCommitmentDiscountAmount())
                    .fee(planField.getMonthlyCharge())
                    .build();
        }
    }
}
