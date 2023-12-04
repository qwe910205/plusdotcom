package com.qwe910205.plusdotcom.plan.repository;

import com.qwe910205.plusdotcom.datainit.service.initializer.ServiceInitializer;
import com.qwe910205.plusdotcom.plan.domain.PremiumService;
import com.qwe910205.plusdotcom.plan.domain.wrapper.ServiceName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PremiumServiceRepositoryTest {

    @Autowired
    PremiumServiceRepository repository;
    @Autowired
    ServiceInitializer initializer;

    @Test
    @DisplayName("프리미엄 서비스 이름으로 프리미엄 서비스를 불러올 수 있다.")
    void findById() {
        String name = "디즈니+";

        PremiumService premiumService = repository.findById(new ServiceName(name)).orElseThrow(IllegalArgumentException::new);

        assertThat(premiumService.getName()).isEqualTo(name);
    }

}