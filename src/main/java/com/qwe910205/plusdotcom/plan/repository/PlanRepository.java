package com.qwe910205.plusdotcom.plan.repository;

import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanName;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    @EntityGraph(attributePaths = {"networkTech", "category"})
    @Override
    List<Plan> findAll();

    Optional<Plan> findByName(PlanName planName);

    @EntityGraph(attributePaths = {"networkTech", "category"})
    Optional<Plan> findByPlanId(PlanId planId);

}
