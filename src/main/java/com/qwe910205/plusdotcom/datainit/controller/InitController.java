package com.qwe910205.plusdotcom.datainit.controller;

import com.qwe910205.plusdotcom.datainit.service.ConvenienceFunctionInitService;
import com.qwe910205.plusdotcom.datainit.service.ManufacturerInitService;
import com.qwe910205.plusdotcom.datainit.service.NetworkTechInitService;
import com.qwe910205.plusdotcom.datainit.service.PhoneInitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class InitController {

    private final ManufacturerInitService manufacturerInitService;
    private final NetworkTechInitService networkTechInitService;
    private final ConvenienceFunctionInitService convenienceFunctionInitService;
    private final PhoneInitService phoneInitService;

    @GetMapping("/init")
    public String init() {
        manufacturerInitService.init();
        networkTechInitService.init();
        convenienceFunctionInitService.init();
        phoneInitService.init();

        return "데이터 초기화 완료";
    }


}
