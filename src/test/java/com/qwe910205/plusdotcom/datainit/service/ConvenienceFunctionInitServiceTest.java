package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phone.domain.ConvenienceFunction;
import com.qwe910205.plusdotcom.phone.repository.ConvenienceFunctionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
class ConvenienceFunctionInitServiceTest {

    @Autowired ConvenienceFunctionInitService service;
    @Autowired
    ConvenienceFunctionRepository repository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Test
    void 편의기능_데이터를_초기화할_수_있다() {
        // given
        String[] names = {"삼성페이", "지문인식", "안면인식"};

        // when
        service.init();
        em.flush();
        em.clear();

        // then
        List<ConvenienceFunction> convenienceFunctions = repository.findAll();
        Assertions.assertThat(convenienceFunctions.size()).isEqualTo(names.length);
        List<String> convenienceFunctionNames = convenienceFunctions.stream().map(ConvenienceFunction::getName).toList();
        Assertions.assertThat(convenienceFunctionNames).containsOnly(names);
    }

}