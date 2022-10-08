package com.qwe910205.plusdotcom.phonemodel.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.ImageSource;
import com.qwe910205.plusdotcom.phonemodel.domain.vo.*;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
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
    private Map<String, PhoneProduct> productMap = new HashMap<>();

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

    protected PhoneModel(PhoneModelId id, PhoneModelName name, Manufacturer manufacturer, NetworkTech networkTech, Price price) {
        Objects.requireNonNull(id, "스마트폰 모델의 아이디는 필수입니다.");
        Objects.requireNonNull(name, "스마트폰 모델명은 필수입니다.");
        Objects.requireNonNull(manufacturer, "스마트폰 모델의 제조사는 필수입니다.");
        Objects.requireNonNull(networkTech, "스마트폰 모델의 통신 기술은 필수입니다.");
        Objects.requireNonNull(price, "스마트폰 모델의 가격은 필수입니다.");
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.networkTech = networkTech;
        this.price = price;
    }

    public String getId() {
        return this.id.getId();
    }

    public String getName() {
        return this.name.getName();
    }

    public void addHashTags(List<HashTag> hashTags) {
        this.hashTags.addAll(hashTags);
    }

    public void setDescription(PhoneDescription description) {
        this.description = description;
    }

    public void addDescriptionImages(List<ImageSource> descriptionImages) {
        this.descriptionImages.addAll(descriptionImages);
    }

    public void addProduct(Color color, List<ImageSource> images, Stock stock) {
        PhoneProduct phoneProduct = new PhoneProduct(this, color, images, stock);
        this.productMap.put(color.getName(), phoneProduct);
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public void setBatteryCapacity(BatteryCapacity batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    public void setMemoryCapacity(MemoryCapacity memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void addConvenienceFunctions(List<ConvenienceFunction> convenienceFunctions) {
        this.convenienceFunctions.addAll(convenienceFunctions);
    }
    public void setThumbnail(ImageSource thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<PhoneProduct> getAllProducts() {
        return this.productMap.values().stream().toList();
    }
}
