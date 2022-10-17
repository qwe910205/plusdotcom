package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.plan.domain.MediaService;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.PremiumService;
import com.qwe910205.plusdotcom.plan.repository.MediaServiceRepository;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import com.qwe910205.plusdotcom.plan.repository.PremiumServiceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceInitializerTest {

    @Autowired ServiceInitializer serviceInitializer;
    @Autowired
    PremiumServiceRepository premiumServiceRepository;
    @Autowired
    MediaServiceRepository mediaServiceRepository;

    @Test
    @DisplayName("프리미엄 서비스와 미디어 서비스를 초기화할 수 있다.")
    void init() {
        serviceInitializer.init();

        List<PremiumService> premiumServices = premiumServiceRepository.findAll();
        List<MediaService> mediaServices = mediaServiceRepository.findAll();
        assertThat(premiumServices.size()).isEqualTo(12);
        assertThat(mediaServices.size()).isEqualTo(4);
    }

}