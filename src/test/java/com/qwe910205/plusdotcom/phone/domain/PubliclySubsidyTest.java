package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.datainit.service.InitService;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanName;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PubliclySubsidyTest {

    @Autowired
    InitService initService;
    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    PlanRepository planRepository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Test
    @DisplayName("스마트폰 모델 데이터를 삭제하면 공시지원금 데이터도 삭제된다.")
    void removePhoneModel() {
        initService.init();
        em.flush();
        em.clear();

        assertThatCode(() -> phoneRepository.deleteAll()).doesNotThrowAnyException();
    }

    @Transactional
    @Test
    @DisplayName("요금제를 삭제하면 공시지원금 데이터도 삭제된다.")
    void deletePlan() {
        initService.init();
        List<PhoneModel> phoneModels = phoneRepository.findAll();
        Map<Long, Integer> sizeMap = new HashMap<>();
        phoneModels.forEach(phoneModel -> sizeMap.put(phoneModel.getId(), phoneModel.getPubliclySubsidies().size()));
        em.flush();
        em.clear();

        Plan plan1 = planRepository.findByName(new PlanName("5G 심플+")).orElseThrow();
        Plan plan2 = planRepository.findByName(new PlanName("LTE 프리미어 에센셜")).orElseThrow();
        planRepository.delete(plan1);
        planRepository.delete(plan2);
        em.flush();
        em.clear();

        List<PhoneModel> all = phoneRepository.findAll();
        for (PhoneModel phoneModel : all) {
            assertThat(phoneModel.getPubliclySubsidies().size()).isEqualTo(sizeMap.get(phoneModel.getId()) - 1);
        }
    }

}