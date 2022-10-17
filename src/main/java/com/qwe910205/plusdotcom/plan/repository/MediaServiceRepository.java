package com.qwe910205.plusdotcom.plan.repository;

import com.qwe910205.plusdotcom.plan.domain.MediaService;
import com.qwe910205.plusdotcom.plan.domain.wrapper.ServiceName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaServiceRepository extends JpaRepository<MediaService, ServiceName> {
}
