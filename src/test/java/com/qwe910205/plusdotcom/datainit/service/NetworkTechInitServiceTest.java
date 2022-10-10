package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.phone.repository.NetworkTechRepository;
import org.assertj.core.api.Assertions;
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
class NetworkTechInitServiceTest {

    @Autowired NetworkTechInitService service;
    @Autowired
    NetworkTechRepository repository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Test
    @DisplayName("통신기술 데이터를 초기화할 수 있다.")
    void initNetworkTechData() {
        String[] names = {"5G", "LTE"};

        service.init();
        em.flush();
        em.clear();

        List<NetworkTech> networkTechs = repository.findAll();
        List<String> networkTechNames = networkTechs.stream().map(NetworkTech::getName).toList();
        assertThat(networkTechNames).containsOnly(names);
    }

}