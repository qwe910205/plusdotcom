package com.qwe910205.plusdotcom.phonemodel.repository;

import com.qwe910205.plusdotcom.phonemodel.domain.NetworkTech;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.NetworkTechName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkTechRepository extends JpaRepository<NetworkTech, NetworkTechName> {
}
