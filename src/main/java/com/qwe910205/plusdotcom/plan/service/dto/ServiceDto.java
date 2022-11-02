package com.qwe910205.plusdotcom.plan.service.dto;

import com.qwe910205.plusdotcom.plan.domain.Service;
import lombok.Builder;

@Builder
public record ServiceDto(String name, String imageUrl) {

    public static ServiceDto createFrom(Service service) {
        return ServiceDto.builder()
                .name(service.getName())
                .imageUrl(service.getImage())
                .build();
    }
}
