package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.plan.domain.PlanCategory;
import com.qwe910205.plusdotcom.plan.repository.PlanCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PlanCategoryInitializer implements DataInitializer {

    private int priority = 0;

    private final PlanCategoryRepository planCategoryRepository;

    private final String[] CATEGORY_NAMES = {"데이터 무제한", "데이터 일반", "시니어", "청소년", "복지", "현역병사", "다이렉트"};

    @Override
    public void init() {
        for (String categoryName : CATEGORY_NAMES) {
            PlanCategory planCategory = new PlanCategory(categoryName);
            planCategoryRepository.save(planCategory);
        }
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(DataInitializer o) {
        return this.priority - o.getPriority();
    }
}
