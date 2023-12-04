package com.qwe910205.plusdotcom.phone.service.dto;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import lombok.Builder;

import java.util.List;

@Builder
public record PhoneDto(String modelId, String modelName, String manufacturer, String networkTech, List<String> hashtags,
                       List<String> descriptionImages, PhoneDescriptionDto description, Integer price,
                       List<PhoneProductDto> products, List<String> convenienceFunctions) {

    public static PhoneDto createFrom(PhoneModel phoneModel) {
        return PhoneDto.builder()
                .modelId(phoneModel.getModelId())
                .modelName(phoneModel.getName())
                .manufacturer(phoneModel.getManufacturer())
                .networkTech(phoneModel.getNetworkTech())
                .hashtags(phoneModel.getHashtags())
                .descriptionImages(phoneModel.getDescriptionImages())
                .description(PhoneDescriptionDto.createFrom(phoneModel.getDescription()))
                .price(phoneModel.getMoney())
                .products(phoneModel.getAllProducts().stream().map(PhoneProductDto::createFrom).toList())
                .convenienceFunctions(phoneModel.getConvenienceFunctions())
                .build();
    }
}
