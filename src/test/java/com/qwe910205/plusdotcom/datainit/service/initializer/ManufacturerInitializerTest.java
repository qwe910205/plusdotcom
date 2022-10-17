package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.datainit.service.initializer.ManufacturerInitializer;
import com.qwe910205.plusdotcom.phone.domain.Manufacturer;
import com.qwe910205.plusdotcom.phone.repository.ManufacturerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ManufacturerInitializerTest {

    @Autowired
    ManufacturerInitializer service;
    @Autowired
    ManufacturerRepository repository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Test
    @DisplayName("제조사 데이터를 초기화할 수 있다.")
    void initManufacturerData() {
        String[] names = {"SAMSUNG", "APPLE"};

        service.init();
        em.flush();
        em.clear();

        List<Manufacturer> manufacturers = repository.findAll();
        List<String> manufacturerNames = manufacturers.stream().map(Manufacturer::getName).toList();
        assertThat(manufacturerNames).containsOnly(names);
    }
}