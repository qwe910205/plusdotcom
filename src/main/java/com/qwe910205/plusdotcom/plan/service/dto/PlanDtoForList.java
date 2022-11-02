package com.qwe910205.plusdotcom.plan.service.dto;

import com.qwe910205.plusdotcom.plan.domain.Plan;
import lombok.Builder;

@Builder
public record PlanDtoForList(String planId, String name, String networkTech, Integer monthlyPayment, String category,
                             PlanDescriptionDto description) {

    public static PlanDtoForList createFrom(Plan plan) {
        return PlanDtoForList.builder()
                .planId(plan.getPlanId())
                .name(plan.getName())
                .networkTech(plan.getNetworkTech())
                .monthlyPayment(plan.getBasicMonthlyCharge())
                .category(plan.getCategory())
                .description(PlanDescriptionDto.createFrom(plan.getDescription()))
                .build();
    }
}
