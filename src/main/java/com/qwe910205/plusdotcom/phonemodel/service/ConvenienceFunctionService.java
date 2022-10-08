package com.qwe910205.plusdotcom.phonemodel.service;

import com.qwe910205.plusdotcom.phonemodel.domain.ConvenienceFunction;
import com.qwe910205.plusdotcom.phonemodel.repository.ConvenienceFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConvenienceFunctionService {

    private final ConvenienceFunctionRepository convenienceFunctionRepository;

    public void save(String convenienceFunctionName) {
        convenienceFunctionRepository.save(new ConvenienceFunction(convenienceFunctionName));
    }
}
