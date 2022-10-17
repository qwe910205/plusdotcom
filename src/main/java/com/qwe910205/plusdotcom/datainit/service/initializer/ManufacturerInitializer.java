package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.phone.domain.Manufacturer;
import com.qwe910205.plusdotcom.phone.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class ManufacturerInitializer implements DataInitializer {

    private int priority = 0;

    private final ManufacturerRepository manufacturerRepository;
    private final String[] MANUFACTURER_NAMES = {"SAMSUNG", "APPLE"};

    @Override
    public void init() {
        for (String name : MANUFACTURER_NAMES) {
            manufacturerRepository.save(new Manufacturer(name));
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
