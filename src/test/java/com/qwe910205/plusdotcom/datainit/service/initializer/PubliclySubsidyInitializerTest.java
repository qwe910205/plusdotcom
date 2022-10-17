package com.qwe910205.plusdotcom.datainit.service.initializer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PubliclySubsidyInitializerTest {

    @Autowired ConvenienceFunctionInitializer convenienceFunctionInitializer;
    @Autowired ManufacturerInitializer manufacturerInitializer;
    @Autowired NetworkTechInitializer networkTechInitializer;
    @Autowired PhoneInitializer phoneInitializer;
    @Autowired PlanCategoryInitializer planCategoryInitializer;
    @Autowired PlanInitializer planInitializer;
    @Autowired PubliclySubsidyInitializer publiclySubsidyInitializer;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void initRequiredData() {
        convenienceFunctionInitializer.init();
        clear();
        manufacturerInitializer.init();
        clear();
        networkTechInitializer.init();
        clear();
        planCategoryInitializer.init();
        clear();
        phoneInitializer.init();
        clear();
        planInitializer.init();
        clear();
    }

    @Transactional
    @Test
    @DisplayName("Json 파일을 읽어 공시지원금 데이터를 저장할 수 있다.")
    void initPubliclySubsidy() {
        publiclySubsidyInitializer.init();
    }

    private void clear() {
        em.flush();
        em.clear();
    }
}