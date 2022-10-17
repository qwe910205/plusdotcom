package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.ColorCode;
import com.qwe910205.plusdotcom.phone.domain.wrapper.ColorName;
import com.qwe910205.plusdotcom.phone.domain.wrapper.ImageSource;
import com.qwe910205.plusdotcom.phone.domain.wrapper.Stock;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"phoneModel", "colorName"})
@Table(uniqueConstraints = @UniqueConstraint(
        name = "PHONE_MODEL_COLOR_NAME_UNIQUE",
        columnNames = {"PHONE_MODEL_ID", "COLOR_NAME"}
))
@Entity
public class PhoneProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private PhoneModel phoneModel;

    @AttributeOverride(name = "name", column = @Column(name = "COLOR_NAME", nullable = false, updatable = false))
    @Embedded
    private ColorName colorName;

    @AttributeOverride(name = "code", column = @Column(name = "COLOR_CODE", nullable = false))
    @Embedded
    private ColorCode colorCode;

    @OrderColumn
    @ElementCollection
    private List<ImageSource> images = new ArrayList<>();

    @Embedded
    private Stock stock;

    PhoneProduct(PhoneModel phoneModel, String colorName, String colorCode, List<String> images, int stock) {
        this.phoneModel = phoneModel;
        this.colorName = new ColorName(colorName);
        this.colorCode = new ColorCode(colorCode);
        if (images != null)
            this.images.addAll(images.stream().map(ImageSource::new).toList());
        this.stock = new Stock(stock);
    }
}
