package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwe910205.plusdotcom.datainit.service.dto.SimplePhoneDto;
import com.qwe910205.plusdotcom.datainit.service.dto.SimplePhoneDtoList;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelName;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanName;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class PubliclySubsidyInitializer implements DataInitializer {
    private final int priority = 2;
    private final ObjectMapper objectMapper;
    private final PhoneRepository phoneRepository;
    private final PlanRepository planRepository;

    @Override
    public void init() {
        initPubliclySubsidy();
    }

    private void initPubliclySubsidy() {
        SimplePhoneDtoList simplePhoneDtoList;
        try {
            simplePhoneDtoList = objectMapper.readValue(new ClassPathResource("data/publicly-subsidy.json").getFile(), SimplePhoneDtoList.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<SimplePhoneDto> simplePhoneDtos = simplePhoneDtoList.phones();
        for (SimplePhoneDto simplePhoneDto : simplePhoneDtos) {
            PhoneModel phoneModel = phoneRepository.findByName(new PhoneModelName(simplePhoneDto.name())).orElseThrow();
            simplePhoneDto.publiclySubsidies()
                    .forEach(publiclySubsidyDto -> phoneModel.putPubliclySubsidy(planRepository.findByName(new PlanName(publiclySubsidyDto.plan_name())).orElseThrow(), publiclySubsidyDto.amount()));
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(DataInitializer o) {
        return this.priority - o.getPriority();
    }
}
