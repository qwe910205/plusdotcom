package com.qwe910205.plusdotcom.plan.repository;

import com.qwe910205.plusdotcom.plan.domain.PlanCategory;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanCategoryName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanCategoryRepository extends JpaRepository<PlanCategory, PlanCategoryName> {
}
