package com.qwe910205.plusdotcom.phone.repository;

import com.qwe910205.plusdotcom.phone.domain.ConvenienceFunction;
import com.qwe910205.plusdotcom.phone.domain.vo.ConvenienceFunctionName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvenienceFunctionRepository extends JpaRepository<ConvenienceFunction, ConvenienceFunctionName> {
}
