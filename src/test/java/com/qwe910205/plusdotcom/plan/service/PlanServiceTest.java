package com.qwe910205.plusdotcom.plan.service;

import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import com.qwe910205.plusdotcom.plan.service.dto.PlanListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=none", "spring.datasource.url=jdbc:mysql://localhost:3306/uplusdotcom"})
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

}