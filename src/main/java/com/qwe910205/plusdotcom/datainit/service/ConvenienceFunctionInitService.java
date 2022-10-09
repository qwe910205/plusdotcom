package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phonemodel.domain.ConvenienceFunction;
import com.qwe910205.plusdotcom.phonemodel.repository.ConvenienceFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConvenienceFunctionInitService {
    
    private final ConvenienceFunctionRepository convenienceFunctionRepository;
    private final String[] CONVENIENCE_FUNCTION_NAMES = {"삼성페이", "지문인식", "안면인식"};
    
    public void init() {
        for (String name : CONVENIENCE_FUNCTION_NAMES) {
            convenienceFunctionRepository.save(new ConvenienceFunction(name));
        }
    }
}
