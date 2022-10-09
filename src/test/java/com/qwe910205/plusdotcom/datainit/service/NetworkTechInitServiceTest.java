package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.phone.repository.NetworkTechRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
class NetworkTechInitServiceTest {

    @Autowired NetworkTechInitService service;
    @Autowired
    NetworkTechRepository repository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Test
    void 통신기술_데이터를_초기화할_수_있다() {
        // given
        String[] names = {"5G", "LTE"};

        // when
        service.init();
        em.flush();
        em.clear();

        // then
        List<NetworkTech> networkTechs = repository.findAll();
        Assertions.assertThat(networkTechs.size()).isEqualTo(names.length);
        List<String> networkTechNames = networkTechs.stream().map(NetworkTech::getName).toList();
        Assertions.assertThat(networkTechNames).containsOnly(names);
    }

}