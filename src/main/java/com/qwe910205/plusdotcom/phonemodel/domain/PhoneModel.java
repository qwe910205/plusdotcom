package com.qwe910205.plusdotcom.phonemodel.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.ImageSource;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.*;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Entity
public abstract class PhoneModel {

    @EmbeddedId
    private PhoneModelId id;

    @AttributeOverride(name = "name", column = @Column(unique = true, nullable = false))
    @Embedded
    private PhoneModelName name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private NetworkTech networkTech;

    @ElementCollection
    private List<HashTag> hashTags = new ArrayList<>();

    @AttributeOverride(name = "url", column = @Column(name = "THUMBNAIL"))
    @Embedded
    private ImageSource thumbnail;

    @OrderColumn
    @ElementCollection
    private List<ImageSource> descriptionImages = new ArrayList<>();

    @MapKeyColumn(name = "COLOR_NAME")
    @OneToMany(mappedBy = "phoneModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<String, PhoneProduct> products = new HashMap<>();

    @Embedded
    private ScreenSize screenSize;

    @Embedded
    private Size size;

    @Embedded
    private Weight weight;

    @Embedded
    private MemoryCapacity memoryCapacity;

    @Embedded
    private BatteryCapacity batteryCapacity;

    @Embedded
    private PhoneDescription description;

    @AttributeOverride(name = "price", column = @Column(nullable = false))
    @Embedded
    private Price price;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @ManyToMany
    private List<ConvenienceFunction> convenienceFunctions = new ArrayList<>();

    @MapKey(name = "plan")
    @OneToMany(mappedBy = "phoneModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<Long, PubliclySubsidy> publiclySubsidies = new HashMap<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Plan mostRecommendedPlan;

    @ManyToMany
    private List<Plan> recommendedPlans = new ArrayList<>();

    PhoneModel(PhoneModelId id, PhoneModelName name, Manufacturer manufacturer, NetworkTech networkTech, ImageSource thumbnail, ScreenSize screenSize, Size size, Weight weight, MemoryCapacity memoryCapacity, BatteryCapacity batteryCapacity, PhoneDescription description, Price price, LocalDate releaseDate) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.networkTech = networkTech;
        this.thumbnail = thumbnail;
        this.screenSize = screenSize;
        this.size = size;
        this.weight = weight;
        this.memoryCapacity = memoryCapacity;
        this.batteryCapacity = batteryCapacity;
        this.description = description;
        this.price = price;
        this.releaseDate = releaseDate;
    }
}
