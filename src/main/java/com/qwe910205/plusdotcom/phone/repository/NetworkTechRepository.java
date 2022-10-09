package com.qwe910205.plusdotcom.phone.repository;

import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.phone.domain.vo.NetworkTechName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkTechRepository extends JpaRepository<NetworkTech, NetworkTechName> {
}
