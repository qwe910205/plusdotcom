package com.qwe910205.plusdotcom.plan.domain.factory;

import com.qwe910205.plusdotcom.datainit.service.dto.PlanDto;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.PlanDescription;

public class PlanFactory {

    public static Plan create(PlanDto planDto) {
        Plan plan;
        if (planDto.name().contains("다이렉트")) {
            plan = new Plan(planDto.id(), planDto.name(), planDto.network_tech(), planDto.monthly_payment(), "다이렉트");
        } else {
            plan = new Plan(planDto.id(), planDto.name(), planDto.network_tech(), planDto.monthly_payment(), planDto.plan_category());
        }
        PlanDescription planDescription = PlanDescription.builder().shareData(planDto.sharing_data()).callingPolicy(planDto.callingPolicy_calling())
                .messagePolicy(planDto.messagePolicy_msg()).build();
        plan.setDescription(planDescription);
        return plan;
    }

    public static Plan create(String id, String name, String networkTechName, Integer monthlyPayment, String category) {
        return new Plan(id, name, networkTechName, monthlyPayment, category);
    }
}
