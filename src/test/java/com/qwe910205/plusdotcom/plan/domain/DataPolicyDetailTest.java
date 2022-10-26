package com.qwe910205.plusdotcom.plan.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DataPolicyDetailTest {

    @Test
    @DisplayName("초과 데이터 사용량에 대한 비용 정책이 없는 데이터 정책 세부사항이라면 데이터 사용량에 대한 비용은 무조건 0이다.")
    void getChargeAbout() {
        Plan plan = createPlan();
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(plan, 1000), 0, null);

        long charge = dataPolicyDetail.getChargeAbout(10000);

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