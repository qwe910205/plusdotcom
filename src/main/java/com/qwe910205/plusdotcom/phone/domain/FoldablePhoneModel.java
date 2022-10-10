package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    public FoldablePhoneModel(PhoneModelId id, PhoneModelName name, Manufacturer manufacturer, NetworkTech networkTech, Price price) {
        super(id, name, manufacturer, networkTech, price);
    }

    public void setFoldedSize(Size foldedSize) {
        this.foldedSize = foldedSize;
    }
}
