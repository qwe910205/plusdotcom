package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.ConvenienceFunctionName;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name"})
@Entity
public class ConvenienceFunction {

    @AttributeOverride(name = "value", column = @Column(name = "NAME"))
    @EmbeddedId
    private ConvenienceFunctionName name;

    public ConvenienceFunction(String name) {
        this.name = new ConvenienceFunctionName(name);
    }

    public String getName() {
        return this.name.getValue();
    }
}
