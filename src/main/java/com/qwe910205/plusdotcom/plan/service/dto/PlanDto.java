package com.qwe910205.plusdotcom.plan.service.dto;

import com.qwe910205.plusdotcom.plan.domain.Plan;
import lombok.Builder;

import java.util.List;

@Builder
public record PlanDto(String planId, String name, String networkTech, Integer monthlyPayment, String category,
                      PlanDescriptionDto description, List<ServiceDto> premiumServices,
                      List<ServiceDto> mediaServices) {

    public static PlanDto createFrom(Plan plan) {
        return PlanDto.builder()
                .planId(plan.getPlanId())
                .name(plan.getName())
                .networkTech(plan.getNetworkTech())
                .monthlyPayment(plan.getBasicMonthlyCharge())
                .category(plan.getCategory())
                .description(PlanDescriptionDto.createFrom(plan.getDescription()))
                .premiumServices(plan.getPremiumServices().stream().map(ServiceDto::createFrom).toList())
                .mediaServices(plan.getMediaServices().stream().map(ServiceDto::createFrom).toList())
                .build();
    }
}
