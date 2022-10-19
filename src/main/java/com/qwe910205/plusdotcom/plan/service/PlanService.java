package com.qwe910205.plusdotcom.plan.service;

import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import com.qwe910205.plusdotcom.plan.service.dto.PlanListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanListDto getPlans() {
        List<Plan> plans = planRepository.findAll();
        return PlanListDto.create(plans);
    }
}
