package com.qwe910205.plusdotcom.plan.service;

import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import com.qwe910205.plusdotcom.plan.service.dto.PlanDto;
import com.qwe910205.plusdotcom.plan.service.dto.PlanDtoForList;
import com.qwe910205.plusdotcom.plan.service.dto.PlanListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PlanServiceTest {

    @Autowired
    PlanService planService;
    @Autowired
    PlanRepository planRepository;

    @Test
    @DisplayName("요금제 리스트를 요청할 수 있다.")
    void getPlans() {
        PlanListDto plans = planService.getPlans();

        assertThat(plans.plans().size()).isEqualTo(planRepository.count());
        plans.plans().forEach(System.out::println);
    }

    @Nested
    @DisplayName("getPlans 메소드는")
    class Describe_getPlans {

        @Nested
        @DisplayName("비용, 한 달간 최소 데이터 사용량, 속도 제한을 신경쓰는지 여부가 주어지면")
        class Context_with_cases {
            private final int cost = Integer.MAX_VALUE;
            private final long minimumMonthlyDataUsage = Long.MAX_VALUE;
            private final boolean careAboutSpeedLimit = true;

            @Test
            @DisplayName("그에 맞는 요금제들을 DTO로 만든 후 반환한다.")
            void it_returns_PlanListDto() {
                PlanListDto plans = planService.getPlans(cost, minimumMonthlyDataUsage, careAboutSpeedLimit);

                assertThat(plans.plans().stream().map(PlanDtoForList::category))
                        .allMatch(category -> category.equals("데이터 무제한"));
            }
        }
    }

    @Test
    @DisplayName("요금제를 단건으로 요청할 수 있다.")
    void getPlan() {
        String planId = "Z202205252";

        PlanDto plan = planService.getPlan(planId);

        assertThat(plan.planId()).isEqualTo(planId);
        System.out.println(plan);
    }

    @Test
    @DisplayName("존재하지 않은 요금제를 조회하면 예외가 발생한다.")
    void getPlanWithNotExistPlanId() {
        String planId = "null";

        assertThatThrownBy(() -> planService.getPlan(planId)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("특정 요금제의 한 달간 데이터 사용량에 대한 부과 비용을 요청할 수 있다.")
    void getChargeAboutMonthlyDataUsage() {
        String planId = "LPZ0001204";
        long dataUsage = 1000;

        assertThatCode(() -> planService.getChargeAboutMonthlyDataUsage(planId, dataUsage)).doesNotThrowAnyException();
    }

}