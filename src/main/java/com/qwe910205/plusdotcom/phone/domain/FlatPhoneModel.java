package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FlatPhoneModel extends PhoneModel {

    @Builder
    public FlatPhoneModel(String id, String name, String manufacturer, String networkTech, int price) {
        super(id, name, manufacturer, networkTech, price);
    }
}
