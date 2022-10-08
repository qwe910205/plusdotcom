package com.qwe910205.plusdotcom.phonemodel.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.Color;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.ImageSource;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.Stock;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = {"phoneModel", "color"})
@Table(uniqueConstraints = {@UniqueConstraint(
        name = "PHONE_MODEL_COLOR_NAME_UNIQUE",
        columnNames = {"PHONE_MODEL_ID", "COLOR_NAME"}
)})
@Entity
public class PhoneProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private PhoneModel phoneModel;

    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "COLOR_NAME", nullable = false)),
            @AttributeOverride(name = "code", column = @Column(name = "COLOR_CODE"))}
    )
    @Embedded
    private Color color;

    @OrderColumn
    @ElementCollection
    private List<ImageSource> images = new ArrayList<>();

    @Embedded
    private Stock stock;
}
