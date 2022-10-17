package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.phone.repository.NetworkTechRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class NetworkTechInitializer implements DataInitializer {

    private int priority = 0;

    private final NetworkTechRepository networkTechRepository;
    private final String[] NETWORK_TECH_NAMES = {"5G", "LTE"};

    @Override
    public void init() {
        for (String name : NETWORK_TECH_NAMES) {
            networkTechRepository.save(new NetworkTech(name));
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
