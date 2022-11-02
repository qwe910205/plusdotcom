package com.qwe910205.plusdotcom.plan.repository;

import com.qwe910205.plusdotcom.plan.domain.PremiumService;
import com.qwe910205.plusdotcom.plan.domain.wrapper.ServiceName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PremiumServiceRepository extends JpaRepository<PremiumService, ServiceName> {
}
