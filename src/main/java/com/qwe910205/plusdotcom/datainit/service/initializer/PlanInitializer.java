package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwe910205.plusdotcom.datainit.service.dto.PlanDto;
import com.qwe910205.plusdotcom.datainit.service.dto.PlanDtoList;
import com.qwe910205.plusdotcom.plan.domain.DataPolicyUnitPeriod;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class PlanInitializer implements DataInitializer {

    private final int priority = 1;

    private final PlanRepository planRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void init() {
        Map<String, Plan> planMap = convertJsonToPlans("data/plans.json");
        deletePlanForInit(planMap);
        initDataPolicy(planMap);
        initPolicyDetail(planMap);
        planRepository.saveAll(planMap.values());
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(DataInitializer o) {
        return this.priority - o.getPriority();
    }

    public Map<String, Plan> convertJsonToPlans(String resource) {
        PlanDtoList planDtoList;
        try {
            planDtoList = objectMapper.readValue(new ClassPathResource(resource).getFile(), PlanDtoList.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Plan> planMap = new HashMap<>();
        for (PlanDto planDto : planDtoList.plans()) {
            if (planDto.plan_category().equals("3G")) continue;
            if (planDto.name().contains("스마트기기") || planDto.name().contains("Wearable") || planDto.name().contains("태블릿"))
                continue;
            Plan plan = planDto.toPlan();
            planMap.put(plan.getName(), plan);
        }
        return planMap;
    }

    public void deletePlanForInit(Map<String, Plan> planMap) {
        planMap.remove("LTE 선택형 요금제"); // 너무 복잡해서
    }

    public void initDataPolicy(Map<String, Plan> planMap) {
        setDataPolicy(planMap, "LTE 프리미어 플러스", DataPolicyUnitPeriod.MONTH, null);
        setDataPolicy(planMap, "LTE 프리미어 에센셜", DataPolicyUnitPeriod.MONTH, null);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 69", DataPolicyUnitPeriod.DAY, 5000);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 59", DataPolicyUnitPeriod.MONTH, 6600);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 49", DataPolicyUnitPeriod.MONTH, 3500);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 44", DataPolicyUnitPeriod.MONTH, 2500);
        setDataPolicy(planMap, "LTE 데이터 33", DataPolicyUnitPeriod.MONTH, 1500);
        setDataPolicy(planMap, "LTE 표준 요금제", DataPolicyUnitPeriod.MONTH, 0);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 시니어 69", DataPolicyUnitPeriod.DAY, 5000);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 시니어 49", DataPolicyUnitPeriod.MONTH, 5000);
        setDataPolicy(planMap, "LTE 데이터 시니어 33", DataPolicyUnitPeriod.MONTH, 1700);
        setDataPolicy(planMap, "LTE 시니어 16.5 요금제(New 시니어 A)", DataPolicyUnitPeriod.MONTH, 300);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 청소년 69", DataPolicyUnitPeriod.DAY, 5000);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 청소년 59", DataPolicyUnitPeriod.MONTH, 9000);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 청소년 49", DataPolicyUnitPeriod.MONTH, 6000);
        setDataPolicy(planMap, "추가 요금 걱정 없는 데이터 청소년 33", DataPolicyUnitPeriod.MONTH, 2000);
        setDataPolicy(planMap, "LTE 청소년 19", DataPolicyUnitPeriod.MONTH, 350);
        setDataPolicy(planMap, "LTE 키즈 39", DataPolicyUnitPeriod.MONTH, 5500);
        setDataPolicy(planMap, "LTE 키즈 29", DataPolicyUnitPeriod.MONTH, 3300);
        setDataPolicy(planMap, "LTE 키즈 22", DataPolicyUnitPeriod.MONTH, 700);
        setDataPolicy(planMap, "LTE 복지 49", DataPolicyUnitPeriod.MONTH, 6000);
        setDataPolicy(planMap, "LTE 복지 33", DataPolicyUnitPeriod.MONTH, 2000);
        setDataPolicy(planMap, "현역병사 데이터 55", DataPolicyUnitPeriod.DAY, 5000);
        setDataPolicy(planMap, "현역병사 데이터 33", DataPolicyUnitPeriod.DAY, 2000);
        setDataPolicy(planMap, "현역병사 데이터 33", DataPolicyUnitPeriod.MONTH, 2000);
        setDataPolicy(planMap, "LTE 다이렉트 45", DataPolicyUnitPeriod.DAY, 5000);
        setDataPolicy(planMap, "5G 시그니처", DataPolicyUnitPeriod.MONTH, null);
        setDataPolicy(planMap, "5G 프리미어 슈퍼", DataPolicyUnitPeriod.MONTH, null);
        setDataPolicy(planMap, "5G 프리미어 플러스", DataPolicyUnitPeriod.MONTH, null);
        setDataPolicy(planMap, "5G 프리미어 레귤러", DataPolicyUnitPeriod.MONTH, null);
        setDataPolicy(planMap, "5G 프리미어 에센셜", DataPolicyUnitPeriod.MONTH, null);
        setDataPolicy(planMap, "5G 복지 55", DataPolicyUnitPeriod.MONTH, 12000);
        setDataPolicy(planMap, "5G 복지 75", DataPolicyUnitPeriod.MONTH, 150000);
        setDataPolicy(planMap, "5G 스탠다드", DataPolicyUnitPeriod.MONTH, 150000);
        setDataPolicy(planMap, "5G 심플+", DataPolicyUnitPeriod.MONTH, 31000);
        setDataPolicy(planMap, "5G 라이트+", DataPolicyUnitPeriod.MONTH, 12000);
        setDataPolicy(planMap, "5G 슬림+", DataPolicyUnitPeriod.MONTH, 6000);
        setDataPolicy(planMap, "5G 라이트 시니어", DataPolicyUnitPeriod.MONTH, 8000);
        setDataPolicy(planMap, "5G 라이트 청소년", DataPolicyUnitPeriod.MONTH, 8000);
        setDataPolicy(planMap, "5G 키즈 45", DataPolicyUnitPeriod.MONTH, 9000);
        setDataPolicy(planMap, "5G 키즈 39", DataPolicyUnitPeriod.MONTH, 5500);
        setDataPolicy(planMap, "5G 키즈 29", DataPolicyUnitPeriod.MONTH, 3300);
        setDataPolicy(planMap, "5G 다이렉트 65", DataPolicyUnitPeriod.MONTH, null);
        setDataPolicy(planMap, "5G 다이렉트 51", DataPolicyUnitPeriod.MONTH, 150000);
        setDataPolicy(planMap, "5G 다이렉트 44", DataPolicyUnitPeriod.MONTH, 31000);
        setDataPolicy(planMap, "5G 다이렉트 37.5", DataPolicyUnitPeriod.MONTH, 12000);
        setDataPolicy(planMap, "5G 다이렉트 34", DataPolicyUnitPeriod.MONTH, 8000);
    }

    private void setDataPolicy(Map<String, Plan> planMap, String name, DataPolicyUnitPeriod unitPeriod, Integer dataQuantity) {
        Plan plan = planMap.get(name);
        if (Objects.nonNull(dataQuantity)) {
            plan.putLimitedDataPolicy(unitPeriod, dataQuantity);
            return;
        }
        plan.putUnLimitedDataPolicy(unitPeriod);
    }

    public void initPolicyDetail(Map<String, Plan> planMap) {
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 69", DataPolicyUnitPeriod.DAY, 0, 5000L);
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 59", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 49", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 44", DataPolicyUnitPeriod.MONTH, 0, 400L);
        addDataPolicyDetail(planMap, "LTE 데이터 33", DataPolicyUnitPeriod.MONTH, 0, null, 1000, 22.53, null);
        addDataPolicyDetail(planMap, "LTE 데이터 33", DataPolicyUnitPeriod.MONTH, 3000, 200L, 1, 19800.0, 19800);
        addDataPolicyDetail(planMap, "LTE 표준 요금제", DataPolicyUnitPeriod.MONTH, 0, null, 1, 0.28, null);
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 시니어 69", DataPolicyUnitPeriod.DAY, 0, 5000L);
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 시니어 49", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "LTE 데이터 시니어 33", DataPolicyUnitPeriod.MONTH, 0, null, 1000, 22.53, null);
        addDataPolicyDetail(planMap, "LTE 데이터 시니어 33", DataPolicyUnitPeriod.MONTH, 3000, 200L, 1, 19800.0, 19800);
        addDataPolicyDetail(planMap, "LTE 시니어 16.5 요금제(New 시니어 A)", DataPolicyUnitPeriod.MONTH, 0, 0L);
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 청소년 69", DataPolicyUnitPeriod.DAY, 0, 5000L);
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 청소년 59", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 청소년 49", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "추가 요금 걱정 없는 데이터 청소년 33", DataPolicyUnitPeriod.MONTH, 0, 400L);
        addDataPolicyDetail(planMap, "LTE 청소년 19", DataPolicyUnitPeriod.MONTH, 0, 0L);
        addDataPolicyDetail(planMap, "LTE 키즈 39", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "LTE 키즈 29", DataPolicyUnitPeriod.MONTH, 0, 400L);
        addDataPolicyDetail(planMap, "LTE 키즈 22", DataPolicyUnitPeriod.MONTH, 0, 400L);
        addDataPolicyDetail(planMap, "LTE 복지 49", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "LTE 복지 33", DataPolicyUnitPeriod.MONTH, 0, 0L);
        addDataPolicyDetail(planMap, "현역병사 데이터 55", DataPolicyUnitPeriod.DAY, 0, 5000L);
        addDataPolicyDetail(planMap, "현역병사 데이터 33", DataPolicyUnitPeriod.DAY, 0, 3000L);
        addDataPolicyDetail(planMap, "현역병사 데이터 33", DataPolicyUnitPeriod.MONTH, 0, 3000L);
        addDataPolicyDetail(planMap, "LTE 다이렉트 45", DataPolicyUnitPeriod.DAY, 0, 5000L);
        addDataPolicyDetail(planMap, "5G 복지 55", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "5G 복지 75", DataPolicyUnitPeriod.MONTH, 0, 5000L);
        addDataPolicyDetail(planMap, "5G 스탠다드", DataPolicyUnitPeriod.MONTH, 0, 5000L);
        addDataPolicyDetail(planMap, "5G 심플+", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "5G 라이트+", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "5G 슬림+", DataPolicyUnitPeriod.MONTH, 0, 400L);
        addDataPolicyDetail(planMap, "5G 라이트 시니어", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "5G 라이트 청소년", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "5G 키즈 45", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "5G 키즈 39", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "5G 키즈 29", DataPolicyUnitPeriod.MONTH, 0, 400L);
        addDataPolicyDetail(planMap, "5G 다이렉트 51", DataPolicyUnitPeriod.MONTH, 0, 5000L);
        addDataPolicyDetail(planMap, "5G 다이렉트 44", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "5G 다이렉트 37.5", DataPolicyUnitPeriod.MONTH, 0, 1000L);
        addDataPolicyDetail(planMap, "5G 다이렉트 34", DataPolicyUnitPeriod.MONTH, 0, 400L);
    }

    private void addDataPolicyDetail(Map<String, Plan> planMap, String name, DataPolicyUnitPeriod unitPeriod, int dataBoundary, Long speedLimit) {
        Plan plan = planMap.get(name);
        plan.addDataPolicyDetailThatHasNotAdditionalCharge(unitPeriod, dataBoundary, speedLimit);
    }

    private void addDataPolicyDetail(Map<String, Plan> planMap, String name, DataPolicyUnitPeriod unitPeriod, int dataBoundary, Long speedLimit, Integer dataUnit, Double cost, Integer maximumCharge) {
        Plan plan = planMap.get(name);
        plan.addDataPolicyDetail(unitPeriod, dataBoundary, speedLimit, dataUnit, cost, maximumCharge);
    }
}
