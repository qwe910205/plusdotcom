package com.qwe910205.plusdotcom.phone.service;

import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.phone.service.dto.PhoneDto;
import com.qwe910205.plusdotcom.phone.service.dto.PhoneListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PhoneServiceTest {

    @Autowired PhoneService phoneService;
    @Autowired
    PhoneRepository phoneRepository;

    @Test
    @DisplayName("스마트폰 모델 리스트를 요청할 수 있다.")
    void getPhones() {
        PhoneListDto phones = phoneService.getPhones();

        assertThat(phones.phones().size()).isEqualTo(phoneRepository.count());
        phones.phones().forEach(System.out::println);
    }

    @Test
    @DisplayName("스마트폰 모델의 상세 정보를 요청할 수 있다.")
    void getPhone() {
        String modelId = "RU-SM-G981N";

        PhoneDto phone = phoneService.getPhone(modelId);

        assertThat(phone.modelId()).isEqualTo(modelId);
        System.out.println(phone);
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 스마트폰 모델을 요청하면 예외가 발생한다.")
    void getPhoneWithNotExistModelId() {
        String modelId = "null";

        assertThatThrownBy(() -> phoneService.getPhone(modelId)).isInstanceOf(NoSuchElementException.class);
    }

}