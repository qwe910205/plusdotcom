package com.qwe910205.plusdotcom.phonemodel.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.ManufacturerName;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(of = {"name"})
@Entity
public class Manufacturer {

    @EmbeddedId
    private ManufacturerName name;
}
