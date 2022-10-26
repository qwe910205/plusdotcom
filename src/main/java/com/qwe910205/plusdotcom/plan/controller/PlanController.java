package com.qwe910205.plusdotcom.plan.controller;

import com.qwe910205.plusdotcom.plan.service.PlanService;
import com.qwe910205.plusdotcom.plan.service.dto.PlanDto;
import com.qwe910205.plusdotcom.plan.service.dto.PlanListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    /** 데이터 사용량 단위는 MB */
    @GetMapping("/{planId}/charge")
    public long getChargeAboutMonthlyDataUsage(@PathVariable String planId, @RequestParam long monthlyDataUsage) {
        if (monthlyDataUsage > 10000000)
            throw new IllegalArgumentException("한 달간 데이터 사용량은 10TB를 초과할 수 없습니다.");

        return planService.getChargeAboutMonthlyDataUsage(planId, monthlyDataUsage);
    }
}
