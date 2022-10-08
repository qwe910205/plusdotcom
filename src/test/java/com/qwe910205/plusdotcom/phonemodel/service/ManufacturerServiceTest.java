package com.qwe910205.plusdotcom.phonemodel.service;

import com.qwe910205.plusdotcom.phonemodel.domain.Manufacturer;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.ManufacturerName;
import com.qwe910205.plusdotcom.phonemodel.repository.ManufacturerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManufacturerServiceTest {

    @Autowired ManufacturerService service;
    @Autowired
    ManufacturerRepository repository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Test
    void 제조사를_저장할_수_있다() {
        // given
        String name1 = "삼성";
        String name2 = "애플";

        // when
        service.save(name1);
        service.save(name2);
        em.flush();
        em.clear();

        // then
        Manufacturer findManufacturer1 = repository.findById(new ManufacturerName(name1)).orElseThrow();
        Manufacturer findManufacturer2 = repository.findById(new ManufacturerName(name2)).orElseThrow();
        Assertions.assertThat(repository.count()).isEqualTo(2);
        Assertions.assertThat(findManufacturer1.getName()).isEqualTo(name1);
        Assertions.assertThat(findManufacturer2.getName()).isEqualTo(name2);
    }

    @Transactional
    @Test
    void 중복된_제조사명은_저장할_수_없다() {
        // given
        String name1 = "삼성";
        String name2 = "애플";
        service.save(name1);
        service.save(name2);
        em.flush();
        em.clear();

        // when
        String newName = "샤오미";
        service.save(name1);
        service.save(newName);
        em.flush();
        em.clear();

        // then
        Assertions.assertThat(repository.count()).isEqualTo(3);
    }

    @Transactional
    @Test
    void 잘못된_제조사명은_저장할_수_없다() {
        // given
        String name1 = " ";
        String name2 = null;

        // when
        Assertions.assertThatThrownBy(() -> service.save(name1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> service.save(name2)).isInstanceOf(IllegalArgumentException.class);
    }
}