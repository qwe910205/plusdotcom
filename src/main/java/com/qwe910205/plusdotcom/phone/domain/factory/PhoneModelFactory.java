package com.qwe910205.plusdotcom.phone.domain.factory;

import com.qwe910205.plusdotcom.phone.domain.*;
import com.qwe910205.plusdotcom.phone.domain.vo.PhoneModelId;
import com.qwe910205.plusdotcom.phone.domain.vo.PhoneModelName;
import com.qwe910205.plusdotcom.phone.domain.vo.Price;

public class PhoneModelFactory {

    public static PhoneModel create(String id, String name, String manufacturerName, String networkTechName, int price) {
        PhoneModelId phoneModelId = new PhoneModelId(id);
        PhoneModelName phoneModelName = new PhoneModelName(name);
        Manufacturer manufacturer = new Manufacturer(manufacturerName);
        NetworkTech networkTech = new NetworkTech(networkTechName);
        Price phonePrice = new Price(price);
        if (name.contains("Z")) {
            return FoldablePhoneModel.builder().id(phoneModelId).name(phoneModelName).networkTech(networkTech)
                    .manufacturer(manufacturer).price(phonePrice).build();
        }
        return FlatPhoneModel.builder().id(phoneModelId).name(phoneModelName).networkTech(networkTech)
                .manufacturer(manufacturer).price(phonePrice).build();
    }
}
