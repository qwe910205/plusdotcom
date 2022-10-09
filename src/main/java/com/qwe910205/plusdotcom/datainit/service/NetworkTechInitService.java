package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phone.domain.NetworkTech;
import com.qwe910205.plusdotcom.phone.repository.NetworkTechRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NetworkTechInitService {

    private final NetworkTechRepository networkTechRepository;
    private final String[] NETWORK_TECH_NAMES = {"5G", "LTE"};

    public void init() {
        for (String name : NETWORK_TECH_NAMES) {
            networkTechRepository.save(new NetworkTech(name));
        }
    }
}
