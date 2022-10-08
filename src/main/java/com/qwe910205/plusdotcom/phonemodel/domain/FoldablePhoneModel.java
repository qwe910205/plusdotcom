package com.qwe910205.plusdotcom.phonemodel.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FoldablePhoneModel extends PhoneModel {

    @AttributeOverrides({
            @AttributeOverride(name = "height", column = @Column(name = "FOLDED_HEIGHT")),
            @AttributeOverride(name = "width", column = @Column(name = "FOLDED_WIDTH")),
            @AttributeOverride(name = "thickness", column = @Column(name = "FOLDED_THICKNESS"))}
    )
    @Embedded
    private Size foldedSize;

    @Builder
    FoldablePhoneModel(PhoneModelId id, PhoneModelName name, Manufacturer manufacturer, NetworkTech networkTech, ImageSource thumbnail, ScreenSize screenSize, Size size, Weight weight, MemoryCapacity memoryCapacity, BatteryCapacity batteryCapacity, PhoneDescription description, Price price, LocalDate releaseDate, Size foldedSize) {
        super(id, name, manufacturer, networkTech, thumbnail, screenSize, size, weight, memoryCapacity, batteryCapacity, description, price, releaseDate);
        this.foldedSize = foldedSize;
    }
}
