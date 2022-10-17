package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.datainit.service.InitService;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.repository.PlanCategoryRepository;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlanServiceInitializerTest {

    @Autowired
    NetworkTechInitializer networkTechInitializer;
    @Autowired
    PlanCategoryInitializer planCategoryInitializer;
    @Autowired
    PlanInitializer planInitializer;
    @Autowired
    ServiceInitializer serviceInitializer;
    @Autowired
    PlanServiceInitializer planServiceInitializer;
    @Autowired
    PlanRepository planRepository;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void initRequiredData() {
        networkTechInitializer.init();
        planCategoryInitializer.init();
        planInitializer.init();
        serviceInitializer.init();
    }

    @Transactional
    @Test
    @DisplayName("요금제가 제공하는 서비스들을 추가할 수 있다.")
    void addServiceToPlan() {
        planServiceInitializer.init();
        em.flush();
        em.clear();

        List<Plan> plans = planRepository.findAll();
        assertThat(plans.stream().map(Plan::getPremiumServices).toList()).anyMatch(premiumServices -> premiumServices.size() >= 1);
        assertThat(plans.stream().map(Plan::getMediaServices).toList()).anyMatch(mediaServices -> mediaServices.size() >= 1);
    }

}