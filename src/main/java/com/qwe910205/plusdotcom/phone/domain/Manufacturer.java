package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.ManufacturerName;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name"})
@Entity
public class Manufacturer {

    @AttributeOverride(name = "value", column = @Column(name = "NAME"))
    @EmbeddedId
    private ManufacturerName name;

    public Manufacturer(String name) {
        this.name = new ManufacturerName(name);
    }

    public String getName() {
        return this.name.getValue();
    }
}
