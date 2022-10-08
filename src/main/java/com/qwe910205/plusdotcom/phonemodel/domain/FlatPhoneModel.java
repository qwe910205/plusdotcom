package com.qwe910205.plusdotcom.phonemodel.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FlatPhoneModel extends PhoneModel {

    @Builder
    public FlatPhoneModel(PhoneModelId id, PhoneModelName name, Manufacturer manufacturer, NetworkTech networkTech, Price price) {
        super(id, name, manufacturer, networkTech, price);
    }
}
