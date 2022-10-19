package com.qwe910205.plusdotcom.plan.service.dto;

import com.qwe910205.plusdotcom.plan.domain.Plan;

import java.util.List;

public record PlanListDto(List<PlanDtoForList> plans) {

    public static PlanListDto create(List<Plan> plans) {
        return new PlanListDto(plans.stream().map(PlanDtoForList::create).toList());
    }
}
