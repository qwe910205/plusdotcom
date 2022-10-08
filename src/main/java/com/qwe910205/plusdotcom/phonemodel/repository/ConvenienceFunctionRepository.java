package com.qwe910205.plusdotcom.phonemodel.repository;

import com.qwe910205.plusdotcom.phonemodel.domain.ConvenienceFunction;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.ConvenienceFunctionName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvenienceFunctionRepository extends JpaRepository<ConvenienceFunction, ConvenienceFunctionName> {
}
