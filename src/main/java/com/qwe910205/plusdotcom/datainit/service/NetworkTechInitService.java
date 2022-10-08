package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phonemodel.domain.NetworkTech;
import com.qwe910205.plusdotcom.phonemodel.repository.NetworkTechRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NetworkTechInitService {

    private final NetworkTechRepository networkTechRepository;

    public void init() {
        networkTechRepository.save(new NetworkTech("5G"));
        networkTechRepository.save(new NetworkTech("LTE"));
    }
}
