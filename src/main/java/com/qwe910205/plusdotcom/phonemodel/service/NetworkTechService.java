package com.qwe910205.plusdotcom.phonemodel.service;

import com.qwe910205.plusdotcom.phonemodel.domain.NetworkTech;
import com.qwe910205.plusdotcom.phonemodel.domain.factory.NetworkTechFactory;
import com.qwe910205.plusdotcom.phonemodel.repository.NetworkTechRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NetworkTechService {

    private final NetworkTechRepository networkTechRepository;

    public void save(String networkTechName) {
        NetworkTech networkTech = NetworkTechFactory.create(networkTechName);
        networkTechRepository.save(networkTech);
    }
}
