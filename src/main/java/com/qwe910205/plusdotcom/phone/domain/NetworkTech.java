package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.NetworkTechName;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(of = {"name"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NetworkTech {

    @AttributeOverride(name = "value", column = @Column(name = "NAME"))
    @EmbeddedId
    private NetworkTechName name;

    public NetworkTech(String name) {
        this.name = new NetworkTechName(name);
    }

    public String getName() {
        return this.name.getValue();
    }
}
