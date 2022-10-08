package com.qwe910205.plusdotcom.phonemodel.domain.factory;

import com.qwe910205.plusdotcom.phonemodel.domain.NetworkTech;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.NetworkTechName;

public class NetworkTechFactory {

    public static NetworkTech create(String name) {
        NetworkTechName networkTechName = new NetworkTechName(name);
        return new NetworkTech(networkTechName);
    }
}
