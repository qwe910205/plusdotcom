package com.qwe910205.plusdotcom.plan.service;

import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import com.qwe910205.plusdotcom.plan.service.dto.PlanDto;
import com.qwe910205.plusdotcom.plan.service.dto.PlanListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanListDto getPlans() {
        List<Plan> plans = planRepository.findAll();
        return PlanListDto.create(plans);
    }

    public PlanDto getPlan(String planId) {
        Plan plan = planRepository.findByPlanId(new PlanId(planId))
                .orElseThrow(() -> new NoSuchElementException("아이디가 " + planId + "인 요금제는 존재하지 않습니다."));

        return PlanDto.create(plan);
    }

    public long getChargeAboutMonthlyDataUsage(String planId, long dataUsage) {
        Plan plan = planRepository.findByPlanId(new PlanId(planId))
                .orElseThrow(() -> new NoSuchElementException("아이디가 " + planId + "인 요금제는 존재하지 않습니다."));

        return plan.getChargeAboutMonthlyDataUsage(dataUsage);
    }
}
