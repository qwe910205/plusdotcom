package com.qwe910205.plusdotcom.phonemodel.service;

import com.qwe910205.plusdotcom.phonemodel.domain.NetworkTech;
import com.qwe910205.plusdotcom.phonemodel.domain.factory.NetworkTechFactory;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.NetworkTechName;
import com.qwe910205.plusdotcom.phonemodel.repository.NetworkTechRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@SpringBootTest
class NetworkTechServiceTest {

    @Autowired NetworkTechService service;
    @Autowired
    NetworkTechRepository repository;
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Test
    void 통신기술을_저장할_수_있다() {
        // given
        String name = "5G";
        NetworkTechName networkTechName = new NetworkTechName(name);

        // when
        service.save(name);
        em.flush();
        em.clear();

        // then
        NetworkTech networkTech = repository.findById(networkTechName).orElseThrow();
        Assertions.assertThat(networkTech.getName()).isEqualTo(name);
        Assertions.assertThat(repository.count()).isEqualTo(1);
    }

    @Transactional
    @Test
    void 통신기술명은_중복될_수_없다() {
        // given
        String name1 = "5G";
        String name2 = "LTE";
        service.save(name1);
        service.save(name2);
        em.flush();
        em.clear();

        // when
        service.save(name1);
        service.save("3G");
        em.flush();
        em.clear();

        // then
        Assertions.assertThat(repository.count()).isEqualTo(3);
    }

    @Transactional
    @Test
    void 잘못된_통신기술명은_저장할_수_없다() {
        // given
        String name1 = " ";
        String name2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> service.save(name1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> service.save(name2)).isInstanceOf(IllegalArgumentException.class);
    }
}