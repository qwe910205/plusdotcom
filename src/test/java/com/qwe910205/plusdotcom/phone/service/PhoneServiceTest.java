package com.qwe910205.plusdotcom.phone.service;

import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.phone.service.dto.PhoneListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=none", "spring.datasource.url=jdbc:mysql://localhost:3306/uplusdotcom"})
@SpringBootTest
class PhoneServiceTest {

    @Autowired PhoneService phoneService;
    @Autowired
    PhoneRepository phoneRepository;

    @Test
    @DisplayName("스마트폰 모델 리스트를 요청하면 그에 맞는 응답을 반환할 수 있다.")
    void getPhones() {
        PhoneListDto phones = phoneService.getPhones();

        assertThat(phones.phones().size()).isEqualTo(phoneRepository.count());
        phones.phones().forEach(System.out::println);
    }

}