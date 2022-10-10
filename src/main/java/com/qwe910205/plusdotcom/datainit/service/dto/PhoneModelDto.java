package com.qwe910205.plusdotcom.datainit.service.dto;

import java.util.List;

public record PhoneModelDto(String name, String model_id, String manufacturer, int price, String hash_tag,
                            List<ColorDto> color, List<String> detail_image, String CPU, String display_description,
                            String camera, String memory_ram, String memory_rom, String battery_capacity,
                            String waterproof) {
}
