package com.qwe910205.plusdotcom.payment.controller;

import com.qwe910205.plusdotcom.payment.service.PaymentService;
import com.qwe910205.plusdotcom.payment.service.dto.PaymentSpecificationDto;
import com.qwe910205.plusdotcom.payment.service.dto.PaymentSpecificationListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping(params = {"modelId", "planId"})
    public PaymentSpecificationDto getPaymentSpecification(@RequestParam String modelId, @RequestParam String planId,
                                                           @RequestParam(required = false, defaultValue = "0") Integer installmentPeriod,
                                                           @RequestParam(required = false, defaultValue = "없음") String discountType) {
        return paymentService.getPaymentSpecification(modelId, planId, installmentPeriod, discountType);
    }

    @GetMapping(params = {"planId"})
    public PaymentSpecificationListDto getPaymentSpecifications(@RequestParam String planId,
                                                               @RequestParam(required = false, defaultValue = "0") Integer installmentPeriod,
                                                               @RequestParam(required = false, defaultValue = "없음") String discountType) {
        return paymentService.getPaymentSpecifications(planId, installmentPeriod, discountType);
    }
}
