package com.qwe910205.plusdotcom.phone.service;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelId;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.phone.service.dto.PhoneDto;
import com.qwe910205.plusdotcom.phone.service.dto.PhoneListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneListDto getPhones() {
        List<PhoneModel> phoneModels = phoneRepository.findAll();
        return PhoneListDto.create(phoneModels);
    }

    public PhoneDto getPhone(String modelId) {
        PhoneModel phoneModel = phoneRepository.findByPhoneModelId(new PhoneModelId(modelId))
                .orElseThrow(() -> new NoSuchElementException("모델 아이디가 " + modelId + "인 스마트폰 모델이 존재하지 않습니다"));
        return PhoneDto.create(phoneModel);
    }
}
