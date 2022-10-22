package com.qwe910205.plusdotcom.plan.service;

import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import com.qwe910205.plusdotcom.plan.service.dto.PlanDto;
import com.qwe910205.plusdotcom.plan.service.dto.PlanListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PlanServiceTest {

    @Autowired PlanService planService;
    @Autowired
    PlanRepository planRepository;

    @Test
    @DisplayName("요금제 리스트를 요청할 수 있다.")
    void getPlans() {
        PlanListDto plans = planService.getPlans();

        assertThat(plans.plans().size()).isEqualTo(planRepository.count());
        plans.plans().forEach(System.out::println);
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

}