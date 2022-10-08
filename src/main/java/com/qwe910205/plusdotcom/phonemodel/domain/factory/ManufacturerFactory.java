package com.qwe910205.plusdotcom.phonemodel.domain.factory;

import com.qwe910205.plusdotcom.phonemodel.domain.Manufacturer;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.ManufacturerName;

public class ManufacturerFactory {

    public static Manufacturer create(String name) {
        ManufacturerName manufacturerName = new ManufacturerName(name);
        return new Manufacturer(manufacturerName);
    }
}
