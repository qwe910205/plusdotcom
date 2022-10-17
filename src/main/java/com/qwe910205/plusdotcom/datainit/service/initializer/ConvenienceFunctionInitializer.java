package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.phone.domain.ConvenienceFunction;
import com.qwe910205.plusdotcom.phone.repository.ConvenienceFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class ConvenienceFunctionInitializer implements DataInitializer {

    private int priority = 0;
    
    private final ConvenienceFunctionRepository convenienceFunctionRepository;
    private final String[] CONVENIENCE_FUNCTION_NAMES = {"삼성페이", "지문인식", "안면인식"};

    @Override
    public void init() {
        for (String name : CONVENIENCE_FUNCTION_NAMES) {
            convenienceFunctionRepository.save(new ConvenienceFunction(name));
        }
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(DataInitializer o) {
        return this.priority - o.getPriority();
    }
}
