package com.qwe910205.plusdotcom.phone.service.dto;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import lombok.Builder;

import java.util.List;

@Builder
public record PhoneDto(String modelId, String modelName, String manufacturer, String networkTech, List<String> descriptionImages,
                       Integer price, List<PhoneProductDto> products, List<String> convenienceFunctions) {

    public static PhoneDto create(PhoneModel phoneModel) {
        return PhoneDto.builder()
                .modelId(phoneModel.getPhoneModelId())
                .modelName(phoneModel.getName())
                .manufacturer(phoneModel.getManufacturer())
                .networkTech(phoneModel.getNetworkTech())
                .descriptionImages(phoneModel.getDescriptionImages())
                .price(phoneModel.getPrice())
                .products(phoneModel.getAllProducts().stream().map(PhoneProductDto::create).toList())
                .convenienceFunctions(phoneModel.getConvenienceFunctions())
                .build();
    }
}
