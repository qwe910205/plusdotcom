package com.qwe910205.plusdotcom.phone.domain.factory;

import com.qwe910205.plusdotcom.phone.domain.FlatPhoneModel;
import com.qwe910205.plusdotcom.phone.domain.FoldablePhoneModel;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneModelFactoryTest {

    @Test
    void 스마트폰_모델을_생성할_수_있다() {
        // given
        String id = "SM-A536N";
        String name = "Galaxy A53 5G";
        String manufacturerName = "SAMSUNG";
        String networkTechName = "5G";
        int price = 599500;

        // when
        PhoneModel phoneModel = PhoneModelFactory.create(id, name, manufacturerName, networkTechName, price);

        // then
        Assertions.assertThat(phoneModel.getId()).isEqualTo(id);
    }

    @Test
    void 스마트폰_모델명에_따라_알맞은_인스턴스를_생성한다() {
        // given
        String id = "SM-A536N";
        String flatPhoneName = "Galaxy A53 5G";
        String foldablePhoneName = "Galaxy Z A53 5G";
        String manufacturerName = "SAMSUNG";
        String networkTechName = "5G";
        int price = 599500;

        // when
        PhoneModel flatPhoneModel = PhoneModelFactory.create(id, flatPhoneName, manufacturerName, networkTechName, price);
        PhoneModel foldablePhoneModel = PhoneModelFactory.create(id, foldablePhoneName, manufacturerName, networkTechName, price);

        // then
        Assertions.assertThat(flatPhoneModel).isInstanceOf(FlatPhoneModel.class);
        Assertions.assertThat(foldablePhoneModel).isInstanceOf(FoldablePhoneModel.class);
    }

}