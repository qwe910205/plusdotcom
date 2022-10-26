package com.qwe910205.plusdotcom.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DataPolicyTest {

    @Test
    @DisplayName("데이터 정책에 기본 데이터 제공량이 존재하지 않으면 무제한 데이터 정책이다.")
    void isUnlimited() {
        Plan plan = createPlan();
        DataPolicy dataPolicy = new DataPolicy(plan, null);

        boolean result = dataPolicy.isUnlimited();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("데이터 사용량이 데이터 정책의 기본 데이터 제공량을 초과하지 않는다면 부과 비용은 0이다.")
    void getAdditionalChargeAboutDataUsageLessThanOrEqualToServingDataQuantity() {
        Plan plan = createPlan();
        DataPolicy dataPolicy = new DataPolicy(plan, 5000);
        long dataUsage = 4999;

        long charge = dataPolicy.getAdditionalChargeAbout(dataUsage);

        assertThat(charge).isZero();
    }

    private Plan createPlan() {
        return Plan.builder()
                .id("Z202205252")
                .name("5G 프리미어 플러스")
                .networkTech("5G")
                .basicMonthlyCharge(105000)
                .category("데이터 무제한")
                .build();
    }

}