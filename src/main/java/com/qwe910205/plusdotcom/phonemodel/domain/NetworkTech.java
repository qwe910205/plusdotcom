package com.qwe910205.plusdotcom.phonemodel.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.NetworkTechName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(of = {"name"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NetworkTech {

    @EmbeddedId
    private NetworkTechName name;

    public NetworkTech(String name) {
        this.name = new NetworkTechName(name);
    }

    public String getName() {
        return this.name.getName();
    }
}
