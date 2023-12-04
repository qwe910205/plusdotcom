package com.qwe910205.plusdotcom.datainit.service.dto;

import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.PlanDescription;

public record PlanDto(String network_tech, String plan_category, String plan_desc, String name, String id,
                      Integer basic_monthly_charge, String brand, String category, String dataPolicy_dataQty,
                      String sharing_data, String callingPolicy_calling, String messagePolicy_msg) {

    public Plan toPlan() {
        Plan plan = Plan.builder()
                .planId(id)
                .name(name)
                .networkTech(network_tech)
                .basicMonthlyCharge(basic_monthly_charge)
                .category(name.contains("다이렉트") ? "다이렉트" : plan_category)
                .build();
        PlanDescription planDescription = PlanDescription.builder()
                .shareData(sharing_data)
                .callingPolicy(callingPolicy_calling)
                .messagePolicy(messagePolicy_msg)
                .build();
        plan.setDescription(planDescription);
        return plan;
    }
}
