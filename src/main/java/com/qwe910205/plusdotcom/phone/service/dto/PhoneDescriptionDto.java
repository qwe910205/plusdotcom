package com.qwe910205.plusdotcom.phone.service.dto;

import com.qwe910205.plusdotcom.phone.domain.PhoneDescription;
import lombok.Builder;

@Builder
public record PhoneDescriptionDto(String cpu, String display, String size, String camera, String memory, String battery,
                                  String waterproof) {

    public static PhoneDescriptionDto create(PhoneDescription phoneDescription) {
        return PhoneDescriptionDto.builder()
                .cpu(phoneDescription.getCpuDescription())
                .display(phoneDescription.getDisplayDescription())
                .size(phoneDescription.getSizeDescription())
                .camera(phoneDescription.getCameraDescription())
                .memory(phoneDescription.getMemoryDescription())
                .battery(phoneDescription.getBatteryDescription())
                .waterproof(phoneDescription.getWaterproofDescription())
                .build();
    }
}
