package com.qwe910205.plusdotcom.payment.service.dto;

import com.qwe910205.plusdotcom.payment.domain.PaymentSpecification;

import java.util.List;

public record PaymentSpecificationListDto(List<PaymentSpecificationDto> paymentSpecifications) {

    public static PaymentSpecificationListDto createFrom(List<PaymentSpecification> paymentSpecifications) {
        return new PaymentSpecificationListDto(paymentSpecifications.stream().map(PaymentSpecificationDto::createFrom).toList());
    }
}
