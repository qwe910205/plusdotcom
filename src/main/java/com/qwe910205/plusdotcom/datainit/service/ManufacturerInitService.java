package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phonemodel.domain.Manufacturer;
import com.qwe910205.plusdotcom.phonemodel.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ManufacturerInitService {

    private final ManufacturerRepository manufacturerRepository;

    public void init() {
        manufacturerRepository.save(new Manufacturer("SAMSUNG"));
        manufacturerRepository.save(new Manufacturer("APPLE"));
    }
}
