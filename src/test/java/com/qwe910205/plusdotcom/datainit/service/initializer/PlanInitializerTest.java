package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.datainit.service.initializer.PlanInitializer;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PlanInitializerTest {

    @Autowired
    PlanInitializer planInitializer;
    @Autowired
    PlanRepository planRepository;

    @Test
    @DisplayName("json 파일을 요금제로 변환할 수 있다.")
    void convertJsonToPlans() {
        String resource = "data/plans.json";

        Map<String, Plan> planMap = planInitializer.convertJsonToPlans(resource);

        assertThat(planMap.size()).isGreaterThan(20);
    }

    @Test
    @DisplayName("요금제들의 데이터 정책을 한 번에 초기화할 수 있다.")
    void setDataPoliciesAtOnce() {
        Map<String, Plan> planMap = createPlanMap();
        assertThat(planMap.values().stream().map(Plan::getDataPolicies).toList())
                .allSatisfy(dataPolicies -> assertThat(dataPolicies.size()).isEqualTo(0));

        planInitializer.initDataPolicy(planMap);

        assertThat(planMap.values().stream().map(Plan::getDataPolicies).toList())
                .allSatisfy(dataPolicies -> assertThat(dataPolicies.size()).isGreaterThanOrEqualTo(1));
    }

    private Map<String, Plan> createPlanMap() {
        Map<String, Plan> planMap = planInitializer.convertJsonToPlans("data/plans.json");
        planInitializer.deletePlanForInit(planMap);
        return planMap;
    }

}