package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phone.domain.ConvenienceFunction;
import com.qwe910205.plusdotcom.phone.repository.ConvenienceFunctionRepository;
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
class ConvenienceFunctionInitServiceTest {

    @Autowired ConvenienceFunctionInitService service;
    @Autowired
    ConvenienceFunctionRepository repository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Test
    @DisplayName("편의기능 데이터를 초기화할 수 있다.")
    void initConvenienceFunctionData() {
        String[] names = {"삼성페이", "지문인식", "안면인식"};

        service.init();
        em.flush();
        em.clear();

        List<ConvenienceFunction> convenienceFunctions = repository.findAll();
        List<String> convenienceFunctionNames = convenienceFunctions.stream().map(ConvenienceFunction::getName).toList();
        assertThat(convenienceFunctionNames).containsOnly(names);
    }

}