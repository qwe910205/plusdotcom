package com.qwe910205.plusdotcom.plan.repository;

import com.qwe910205.plusdotcom.datainit.service.initializer.NetworkTechInitializer;
import com.qwe910205.plusdotcom.datainit.service.initializer.PlanCategoryInitializer;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlanRepositoryTest {

    @Autowired
    NetworkTechInitializer networkTechInitializer;
    @Autowired
    PlanCategoryInitializer planCategoryInitializer;
    @Autowired
    PlanRepository planRepository;

    @BeforeEach
    void initRequiredData() {
        networkTechInitializer.init();
        planCategoryInitializer.init();
    }

    @Test
    @DisplayName("요금제명으로 요금제를 조회할 수 있다.")
    void findByName() {
        String planName = "LTE 프리미엄 요금제";
        Plan plan = Plan.builder().id("LP12312").name(planName).networkTech("5G").monthlyPayment(40000).category("데이터 일반").build();
        planRepository.save(plan);

        Plan findPlan = planRepository.findByName(new PlanName(planName)).orElseThrow();
        assertThat(plan).isEqualTo(findPlan);
    }

}