package com.qwe910205.plusdotcom.phone.controller;

import com.qwe910205.plusdotcom.phone.service.PhoneService;
import com.qwe910205.plusdotcom.phone.service.dto.PhoneListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/phones")
@RestController
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping
    public PhoneListDto getPhones() {
        return phoneService.getPhones();
    }
}
