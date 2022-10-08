package com.qwe910205.plusdotcom.phonemodel.domain.factory;

import com.qwe910205.plusdotcom.phonemodel.domain.NetworkTech;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NetworkTechFactoryTest {

    @Test
    void 팩토리로_생성할_수_있다() {
        // given
        String name = "5G";

        // when
        NetworkTech networkTech = NetworkTechFactory.create(name);

        // then
        Assertions.assertThat(networkTech.getName()).isEqualTo(name);
    }

    @Test
    void 잘못된_이름은_생성할_수_없다() {
        // given
        String name1 = " ";
        String name2 = null;

        // when // then
        Assertions.assertThatThrownBy(() -> NetworkTechFactory.create(name1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> NetworkTechFactory.create(name2)).isInstanceOf(IllegalArgumentException.class);
    }

}