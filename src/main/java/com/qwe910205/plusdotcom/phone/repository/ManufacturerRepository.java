package com.qwe910205.plusdotcom.phone.repository;

import com.qwe910205.plusdotcom.phone.domain.Manufacturer;
import com.qwe910205.plusdotcom.phone.domain.vo.ManufacturerName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, ManufacturerName> {
}
