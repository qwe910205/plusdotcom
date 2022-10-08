package com.qwe910205.plusdotcom.phonemodel.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.NetworkTechName;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(of = {"name"})
@Entity
public class NetworkTech {

    @EmbeddedId
    private NetworkTechName name;
}
