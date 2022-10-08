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
    FlatPhoneModel(PhoneModelId id, PhoneModelName name, Manufacturer manufacturer, NetworkTech networkTech, ImageSource thumbnail, ScreenSize screenSize, Size size, Weight weight, MemoryCapacity memoryCapacity, BatteryCapacity batteryCapacity, PhoneDescription description, Price price, LocalDate releaseDate) {
        super(id, name, manufacturer, networkTech, thumbnail, screenSize, size, weight, memoryCapacity, batteryCapacity, description, price, releaseDate);
    }
}
