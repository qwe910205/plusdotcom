package com.qwe910205.plusdotcom.plan.domain.factory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PhoneModelFactoryTest {

    @Test
    @DisplayName("스마트폰 모델을 생성할 수 있다.")
    void createPhoneModel() {
        int num = 1;

        Assertions.assertThat(num).isOne();
    }
}
