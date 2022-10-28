package com.qwe910205.plusdotcom.phone.service.dto;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.PhoneProduct;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;

@Builder
public record PhoneDtoForList(String modelId, String modelName, String manufacturer, String networkTech,
                              String thumbnailUrl, List<String> colorCodes, Double screenSize, Integer ramCapacity, Integer romCapacity,
                              Integer batteryCapacity, Integer price, Long releaseDate, List<String> convenienceFunctions) {

    public static PhoneDtoForList create(PhoneModel phoneModel) {
        return PhoneDtoForList.builder()
                .modelId(phoneModel.getModelId())
                .modelName(phoneModel.getName())
                .manufacturer(phoneModel.getManufacturer())
                .networkTech(phoneModel.getNetworkTech())
                .thumbnailUrl(phoneModel.getThumbnail())
                .colorCodes(phoneModel.getAllProducts().stream().map(PhoneProduct::getColorCode).toList())
                .screenSize(phoneModel.getScreenSize())
                .ramCapacity(phoneModel.getRamCapacity())
                .romCapacity(phoneModel.getRomCapacity())
                .batteryCapacity(phoneModel.getBatteryCapacity())
                .price(phoneModel.getMoney())
                .releaseDate(Timestamp.valueOf(phoneModel.getReleaseDate().atStartOfDay()).getTime())
                .convenienceFunctions(phoneModel.getConvenienceFunctions())
                .build();
    }
}
