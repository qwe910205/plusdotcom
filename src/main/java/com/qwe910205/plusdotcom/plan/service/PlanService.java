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
        return PlanListDto.createFrom(plans);
    }

    public PlanListDto getPlans(long payment, long minimumMonthlyDataUsage, boolean careAboutSpeedLimit) {
        List<Plan> plans = planRepository.findAll();

        if (careAboutSpeedLimit)
            return PlanListDto.createFrom(
                    plans.stream()
                            .filter(plan -> plan.canCalculateThingsRelatedToMonth() &&
                                    plan.availableMonthlyAmountOfDataWithoutSpeedLimitWhenPayFor(payment) >= minimumMonthlyDataUsage)
                            .toList()
            );
        return PlanListDto.createFrom(
                plans.stream()
                        .filter(plan -> plan.canCalculateThingsRelatedToMonth() &&
                                plan.availableMonthlyAmountOfDataWhenPayFor(payment) >= minimumMonthlyDataUsage)
                        .toList()
        );
    }

    public PlanDto getPlan(String planId) {
        Plan plan = planRepository.findByPlanId(new PlanId(planId))
                .orElseThrow(() -> new NoSuchElementException("아이디가 " + planId + "인 요금제는 존재하지 않습니다."));

        return PlanDto.createFrom(plan);
    }

    public long getChargeAboutMonthlyDataUsage(String planId, long dataUsage) {
        Plan plan = planRepository.findByPlanId(new PlanId(planId))
                .orElseThrow(() -> new NoSuchElementException("아이디가 " + planId + "인 요금제는 존재하지 않습니다."));

        return plan.getChargeAboutMonthlyDataUsage(dataUsage);
    }
}
