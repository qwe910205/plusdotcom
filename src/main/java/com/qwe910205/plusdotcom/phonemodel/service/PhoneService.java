package com.qwe910205.plusdotcom.phonemodel.service;

import com.qwe910205.plusdotcom.phonemodel.domain.PhoneModel;
import com.qwe910205.plusdotcom.phonemodel.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public void saveAll(List<PhoneModel> phoneModels) {
        phoneRepository.saveAll(phoneModels);
    }
}
