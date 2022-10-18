package com.qwe910205.plusdotcom.phone.service.dto;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;

import java.util.List;

public record PhoneListDto(List<PhoneDtoForList> phones) {

    public static PhoneListDto create(List<PhoneModel> phoneModels) {
        return new PhoneListDto(phoneModels.stream().map(PhoneDtoForList::create).toList());
    }
}
