package com.qwe910205.plusdotcom.phone.domain.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PhoneModelFactoryTest {

    @Test
    @DisplayName("스마트폰 모델을 생성할 수 있다.")
    void createPhoneModel() {
        String id = "SM-A536N";
        String name = "Galaxy A53 5G";
        String manufacturerName = "SAMSUNG";
        String networkTechName = "5G";
        int price = 599500;

        assertThatCode(() -> PhoneModelFactory.create(id, name, manufacturerName, networkTechName, price))
                .doesNotThrowAnyException();
    }

}