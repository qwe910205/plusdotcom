package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phone.domain.Manufacturer;
import com.qwe910205.plusdotcom.phone.repository.ManufacturerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
class ManufacturerInitServiceTest {

    @Autowired ManufacturerInitService service;
    @Autowired
    ManufacturerRepository repository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Test
    void 제조사_데이터를_초기화할_수_있다() {
        // given
        String[] names = {"SAMSUNG", "APPLE"};

        // when
        service.init();
        em.flush();
        em.clear();

        // then
        List<Manufacturer> manufacturers = repository.findAll();
        Assertions.assertThat(manufacturers.size()).isEqualTo(names.length);
        List<String> manufacturerNames = manufacturers.stream().map(Manufacturer::getName).toList();
        Assertions.assertThat(manufacturerNames).containsOnly(names);
    }
}