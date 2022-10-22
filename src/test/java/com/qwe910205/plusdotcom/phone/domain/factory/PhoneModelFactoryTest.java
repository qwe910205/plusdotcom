package com.qwe910205.plusdotcom.phone.domain.factory;

import com.qwe910205.plusdotcom.phone.domain.FlatPhoneModel;
import com.qwe910205.plusdotcom.phone.domain.FoldablePhoneModel;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource
    @DisplayName("스마트폰 모델명에 따라 알맞은 인스턴스를 생성할 수 있다.")
    void createInstanceAccordingToName(String modelName, Class<PhoneModel> type) {
        String id = "SM-A536N";
        String manufacturerName = "SAMSUNG";
        String networkTechName = "5G";
        int price = 599500;

        PhoneModel phoneModel = PhoneModelFactory.create(id, modelName, manufacturerName, networkTechName, price);

        assertThat(phoneModel).isInstanceOf(type);
    }

    private static Stream<Arguments> createInstanceAccordingToName() {
        return Stream.of(
            Arguments.of("Galaxy A53 5G", FlatPhoneModel.class),
            Arguments.of("Galaxy Z A53 5G", FoldablePhoneModel.class)
        );
    }

}