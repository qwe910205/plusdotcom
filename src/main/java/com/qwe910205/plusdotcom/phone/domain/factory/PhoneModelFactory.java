package com.qwe910205.plusdotcom.phone.domain.factory;

import com.qwe910205.plusdotcom.phone.domain.*;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelId;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelName;
import com.qwe910205.plusdotcom.phone.domain.wrapper.Price;

public class PhoneModelFactory {

    public static PhoneModel create(String id, String name, String manufacturerName, String networkTechName, int price) {
        if (name.contains("Z")) {
            return FoldablePhoneModel.builder().id(id).name(name).networkTech(networkTechName)
                    .manufacturer(manufacturerName).price(price).build();
        }
        return FlatPhoneModel.builder().id(id).name(name).networkTech(networkTechName)
                .manufacturer(manufacturerName).price(price).build();
    }
}
