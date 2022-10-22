package com.qwe910205.plusdotcom.datainit.controller;

import com.qwe910205.plusdotcom.datainit.service.InitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class InitController {

    private final InitService initService;

    @PostMapping("/init")
    public String init() {
        initService.init();

        return "데이터 초기화 완료";
    }


}
