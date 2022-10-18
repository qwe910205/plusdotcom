package com.qwe910205.plusdotcom.phone.service;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import com.qwe910205.plusdotcom.phone.service.dto.PhoneListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneListDto getPhones() {
        List<PhoneModel> phoneModels = phoneRepository.findAll();
        return PhoneListDto.create(phoneModels);
    }
}
