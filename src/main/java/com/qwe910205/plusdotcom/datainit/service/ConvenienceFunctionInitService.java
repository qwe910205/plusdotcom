package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phonemodel.domain.ConvenienceFunction;
import com.qwe910205.plusdotcom.phonemodel.repository.ConvenienceFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConvenienceFunctionInitService {
    
    private final ConvenienceFunctionRepository convenienceFunctionRepository;
    
    public void init() {
        convenienceFunctionRepository.save(new ConvenienceFunction("삼성페이"));
        convenienceFunctionRepository.save(new ConvenienceFunction("지문인식"));
        convenienceFunctionRepository.save(new ConvenienceFunction("안면인식"));
    }
}
