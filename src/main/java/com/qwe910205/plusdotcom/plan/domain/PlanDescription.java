package com.qwe910205.plusdotcom.plan.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PlanDescription {

    private String shareData;
    private String messagePolicy;
    private String callingPolicy;

    @Builder
    public PlanDescription(String shareData, String messagePolicy, String callingPolicy) {
        this.shareData = shareData;
        this.messagePolicy = messagePolicy;
        this.callingPolicy = callingPolicy;
    }
}
