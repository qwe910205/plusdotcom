package com.qwe910205.plusdotcom.plan.service.dto;

import com.qwe910205.plusdotcom.plan.domain.PlanDescription;
import lombok.Builder;

@Builder
public record PlanDescriptionDto(String shareData, String messagePolicy, String callingPolicy) {

    public static PlanDescriptionDto create(PlanDescription planDescription) {
        return PlanDescriptionDto.builder()
                .shareData(planDescription.getShareData())
                .messagePolicy(planDescription.getMessagePolicy())
                .callingPolicy(planDescription.getCallingPolicy())
                .build();
    }
}
