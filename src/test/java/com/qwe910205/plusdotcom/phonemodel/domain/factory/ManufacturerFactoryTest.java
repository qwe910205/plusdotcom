package com.qwe910205.plusdotcom.phonemodel.domain.factory;

import com.qwe910205.plusdotcom.phonemodel.domain.Manufacturer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerFactoryTest {

    @Test
    void 제조사를_생성할_수_있다() {
        // given
        String name = "삼성";

        // when
        Manufacturer manufacturer = ManufacturerFactory.create(name);

        // then
        Assertions.assertThat(manufacturer.getName()).isEqualTo(name);
    }

    @Test
    void 잘못된_이름은_생성할_수_없다() {
        // given
        String name1 = " ";
        String name2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> ManufacturerFactory.create(name1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> ManufacturerFactory.create(name2)).isInstanceOf(IllegalArgumentException.class);
    }

}