package com.qwe910205.plusdotcom.phone.service.dto;

import com.qwe910205.plusdotcom.phone.domain.PhoneProduct;
import lombok.Builder;

import java.util.List;

@Builder
public record PhoneProductDto(String colorName, String colorCode, List<String> images, Integer stock) {

    public static PhoneProductDto createFrom(PhoneProduct phoneProduct) {
        return PhoneProductDto.builder()
                .colorName(phoneProduct.getColorName())
                .colorCode(phoneProduct.getColorCode())
                .images(phoneProduct.getImages())
                .stock(phoneProduct.getStock())
                .build();
    }
}
