package com.qwe910205.plusdotcom.phone.controller;

import com.qwe910205.plusdotcom.phone.service.PhoneService;
import com.qwe910205.plusdotcom.phone.service.dto.PhoneDto;
import com.qwe910205.plusdotcom.phone.service.dto.PhoneListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/phones")
@RestController
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping
    public PhoneListDto getPhones() {
        return phoneService.getPhones();
    }

    @GetMapping("/{modelId}")
    public PhoneDto getPhone(@PathVariable String modelId) {
        return phoneService.getPhone(modelId);
    }
}
