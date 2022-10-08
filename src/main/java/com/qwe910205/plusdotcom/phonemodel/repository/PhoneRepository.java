package com.qwe910205.plusdotcom.phonemodel.repository;

import com.qwe910205.plusdotcom.phonemodel.domain.PhoneModel;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.PhoneModelId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<PhoneModel, PhoneModelId> {
}
