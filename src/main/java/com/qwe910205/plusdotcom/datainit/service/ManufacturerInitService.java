package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phone.domain.Manufacturer;
import com.qwe910205.plusdotcom.phone.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ManufacturerInitService {

    private final ManufacturerRepository manufacturerRepository;
    private final String[] MANUFACTURER_NAMES = {"SAMSUNG", "APPLE"};

    public void init() {
        for (String name : MANUFACTURER_NAMES) {
            manufacturerRepository.save(new Manufacturer(name));
        }
    }
}
