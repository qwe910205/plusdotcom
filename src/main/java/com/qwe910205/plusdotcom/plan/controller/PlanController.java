package com.qwe910205.plusdotcom.plan.controller;

import com.qwe910205.plusdotcom.plan.service.PlanService;
import com.qwe910205.plusdotcom.plan.service.dto.PlanDto;
import com.qwe910205.plusdotcom.plan.service.dto.PlanListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/plans")
@RestController
public class PlanController {

    private final PlanService planService;

    @GetMapping
    public PlanListDto getPlans() {
        return planService.getPlans();
    }

    @GetMapping("/{planId}")
    public PlanDto getPlan(@PathVariable String planId) {
        return planService.getPlan(planId);
    }
}
