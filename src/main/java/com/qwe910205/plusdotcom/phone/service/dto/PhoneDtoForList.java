package com.qwe910205.plusdotcom.phone.service.dto;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;

import java.sql.Timestamp;
import java.util.List;

public record PhoneDtoForList(String modelId, String modelName, String manufacturer, String networkTech,
                              String thumbnailUrl, Double screenSize, Integer ramCapacity, Integer romCapacity,
                              Integer batteryCapacity, Integer price, Long releaseDate, List<String> convenienceFunctions) {

    public static PhoneDtoForList create(PhoneModel phoneModel) {
        return new PhoneDtoForList(phoneModel.getPhoneModelId(), phoneModel.getName(), phoneModel.getManufacturer(),
                phoneModel.getNetworkTech(), phoneModel.getThumbnail(), phoneModel.getScreenSize(), phoneModel.getRamCapacity(),
                phoneModel.getRomCapacity(), phoneModel.getBatteryCapacity(), phoneModel.getPrice(),
                Timestamp.valueOf(phoneModel.getReleaseDate().atStartOfDay()).getTime(), phoneModel.getConvenienceFunctions());
    }
}
