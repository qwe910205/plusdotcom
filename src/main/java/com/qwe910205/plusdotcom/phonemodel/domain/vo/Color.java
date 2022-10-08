package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Color {

    @Embedded
    private ColorName name;

    @Embedded
    private ColorCode code;

    public Color(String name, String code) {
        this.name = new ColorName(name);
        this.code = new ColorCode(code);
    }

    public String getName() {
        return this.name.getName();
    }
}
