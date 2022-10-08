package com.qwe910205.plusdotcom.phonemodel.service;

import com.qwe910205.plusdotcom.phonemodel.domain.Manufacturer;
import com.qwe910205.plusdotcom.phonemodel.domain.factory.ManufacturerFactory;
import com.qwe910205.plusdotcom.phonemodel.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public void save(String name) {
        Manufacturer manufacturer = ManufacturerFactory.create(name);
        manufacturerRepository.save(manufacturer);
    }
}
