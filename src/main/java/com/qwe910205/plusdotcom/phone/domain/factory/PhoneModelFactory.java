package com.qwe910205.plusdotcom.phone.domain.factory;

import com.qwe910205.plusdotcom.phone.domain.*;

public class PhoneModelFactory {

    public static PhoneModel create(String id, String name, String manufacturerName, String networkTechName, int price) {
        return PhoneModel.builder().id(id).name(name).networkTech(networkTechName)
                .manufacturer(manufacturerName).price(price).build();
    }
}
