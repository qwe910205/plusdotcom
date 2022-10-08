package com.qwe910205.plusdotcom.phonemodel.repository;

import com.qwe910205.plusdotcom.phonemodel.domain.Manufacturer;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.ManufacturerName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, ManufacturerName> {
}
