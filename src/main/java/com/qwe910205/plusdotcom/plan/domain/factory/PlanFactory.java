package com.qwe910205.plusdotcom.plan.domain.factory;

import com.qwe910205.plusdotcom.datainit.service.dto.PlanDto;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.PlanDescription;

public class PlanFactory {

    public static Plan create(PlanDto planDto) {
        Plan plan;
        if (planDto.name().contains("다이렉트")) {
            plan = Plan.builder()
                    .planId(planDto.id())
                    .name(planDto.name())
                    .networkTech(planDto.network_tech())
                    .basicMonthlyCharge(planDto.basic_monthly_charge())
                    .category("다이렉트")
                    .build();
        } else {
            plan = Plan.builder()
                    .planId(planDto.id())
                    .name(planDto.name())
                    .networkTech(planDto.network_tech())
                    .basicMonthlyCharge(planDto.basic_monthly_charge())
                    .category(planDto.plan_category())
                    .build();
        }
        PlanDescription planDescription = PlanDescription.builder().shareData(planDto.sharing_data()).callingPolicy(planDto.callingPolicy_calling())
                .messagePolicy(planDto.messagePolicy_msg()).build();
        plan.setDescription(planDescription);
        return plan;
    }
}
