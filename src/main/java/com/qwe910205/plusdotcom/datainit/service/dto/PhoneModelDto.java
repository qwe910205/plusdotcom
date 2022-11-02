package com.qwe910205.plusdotcom.datainit.service.dto;

import com.qwe910205.plusdotcom.phone.domain.PhoneDescription;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.wrapper.Hashtag;

import java.util.Arrays;
import java.util.List;

public record PhoneModelDto(String name, String model_id, String manufacturer, int price, String hash_tag,
                            List<ColorDto> color, List<String> detail_image, String CPU, String display_description,
                            String camera, String memory_ram, String memory_rom, String battery_capacity,
                            String waterproof) {

    public PhoneModel toPhoneModel(String networkTechName) {
        PhoneModel phoneModel = PhoneModel.builder()
                .modelId(model_id)
                .name(name)
                .manufacturer(manufacturer)
                .networkTech(networkTechName)
                .price(price)
                .build();
        phoneModel.addHashTags(Arrays.stream(hash_tag.split(" ")).toList());
        color.forEach(colorDto ->
                phoneModel.addProduct(colorDto.color_name(), colorDto.color_code(), colorDto.color_image(), 100)
        );
        phoneModel.addDescriptionImages(detail_image);
        PhoneDescription phoneDescription = PhoneDescription.builder()
                .cpuDescription(CPU)
                .displayDescription(display_description)
                .cameraDescription(camera)
                .batteryDescription(battery_capacity)
                .waterproofDescription(waterproof)
                .build();
        phoneModel.setDescription(phoneDescription);

        return phoneModel;
    }
}
