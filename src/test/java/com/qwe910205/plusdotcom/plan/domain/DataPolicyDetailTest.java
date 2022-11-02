package com.qwe910205.plusdotcom.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class DataPolicyDetailTest {

    @Test
    @DisplayName("초과 데이터 사용량에 대한 비용 정책이 없는 데이터 정책 세부사항이라면 데이터 사용량에 대한 비용은 무조건 0이다.")
    void getChargeAbout_1() {
        Plan plan = createPlan();
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(plan, 1000), 0, null);

        long charge = dataPolicyDetail.getChargeAbout(10000);

        assertThat(charge).isZero();
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    @DisplayName("데이터 사용량이 0보다 작거나 같으면 데이터 사용량에 대한 비용은 무조건 0이다.")
    void getChargeAbout_2(long dataUsage) {
        Plan plan = createPlan();
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(plan, 1000), 0, null);

        long charge = dataPolicyDetail.getChargeAbout(dataUsage);

        assertThat(charge).isZero();
    }

    @Test
    @DisplayName("데이터 정책 세부사항이 만약 속도 제한이 0이라면 데이터를 사용할 수 없다.")
    void availableData_1() {
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(createPlan(), 0), 0, 0L);

        boolean available = dataPolicyDetail.availableData();

        assertThat(available).isFalse();
    }

    @Test
    @DisplayName("데이터 정책 세부사항이 만약 속도 제한이 없다면 데이터를 사용할 수 있다.")
    void availableData_2() {
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(createPlan(), 0), 0, null);

        boolean available = dataPolicyDetail.availableData();

        assertThat(available).isTrue();
    }

    private Plan createPlan() {
        return Plan.builder()
                .planId("Z202205252")
                .name("5G 프리미어 플러스")
                .networkTech("5G")
                .basicMonthlyCharge(105000)
                .category("데이터 무제한")
                .build();
    }

}