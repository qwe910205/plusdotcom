package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.*;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode(of = {"phoneModelId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Entity
public abstract class PhoneModel {

    @Id @GeneratedValue
    private Long id;

    @AttributeOverride(name = "id", column = @Column(name = "PHONE_MODEL_ID", unique = true, nullable = false, updatable = false))
    @Embedded
    private PhoneModelId phoneModelId;

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

    @MapKey(name = "colorName")
    @OneToMany(mappedBy = "phoneModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<ColorName, PhoneProduct> productMap = new HashMap<>();

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

    protected PhoneModel(String id, String name, String manufacturer, String networkTech, int price) {
        Objects.requireNonNull(id, "스마트폰 모델의 아이디는 필수입니다.");
        Objects.requireNonNull(name, "스마트폰 모델명은 필수입니다.");
        Objects.requireNonNull(manufacturer, "스마트폰 모델의 제조사는 필수입니다.");
        Objects.requireNonNull(networkTech, "스마트폰 모델의 통신 기술은 필수입니다.");
        this.phoneModelId = new PhoneModelId(id);
        this.name = new PhoneModelName(name);
        this.manufacturer = new Manufacturer(manufacturer);
        this.networkTech = new NetworkTech(networkTech);
        this.price = new Price(price);
    }

    public Long getId() {
        return this.id;
    }

    public String getPhoneModelId() {
        return this.phoneModelId.getId();
    }

    public String getName() {
        if (Objects.isNull(this.name))
            return null;
        return this.name.getName();
    }

    public String getNetworkTech() {
        if (Objects.isNull(this.networkTech))
            return null;
        return this.networkTech.getName();
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

    public void addProduct(String colorName, String colorCode, List<String> images, int stock) {
        PhoneProduct phoneProduct = new PhoneProduct(this, colorName, colorCode, images, stock);
        this.productMap.put(new ColorName(colorName), phoneProduct);
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Integer getWeight() {
        if (Objects.isNull(this.weight))
            return null;
        return this.weight.getWeight();
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Integer getBatteryCapacity() {
        if (Objects.isNull(this.batteryCapacity))
            return null;
        return this.batteryCapacity.getBatteryCapacity();
    }

    public void setBatteryCapacity(BatteryCapacity batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public Double getScreenSize() {
        if (Objects.isNull(this.screenSize))
            return null;
        return this.screenSize.getScreenSize();
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    public MemoryCapacity getMemoryCapacity() {
        return this.memoryCapacity;
    }

    public void setMemoryCapacity(MemoryCapacity memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<ConvenienceFunction> getConvenienceFunctions() {
        return this.convenienceFunctions;
    }

    public void addConvenienceFunctions(List<String> convenienceFunctions) {
        for (String name : convenienceFunctions) {
            ConvenienceFunction convenienceFunction = new ConvenienceFunction(name);
            if (!this.convenienceFunctions.contains(convenienceFunction))
                this.convenienceFunctions.add(convenienceFunction);
        }
    }

    public String getThumbnail() {
        if (Objects.isNull(this.thumbnail))
            return null;
        return this.thumbnail.getUrl();
    }
    public void setThumbnail(String url) {
        this.thumbnail = new ImageSource(url);
    }

    public PhoneDescription getDescription() {
        return this.description;
    }

    public List<PhoneProduct> getAllProducts() {
        return this.productMap.values().stream().toList();
    }

    public int getPubliclySubsidy(Plan plan) {
        if (!this.publiclySubsidies.containsKey(plan.getId()))
            throw new NoSuchElementException("등록되지 않은 요금제입니다.");
        PubliclySubsidy publiclySubsidy = publiclySubsidies.get(plan.getId());
        return publiclySubsidy.getAmount();
    }

    public List<PubliclySubsidy> getPubliclySubsidies() {
        return this.publiclySubsidies.values().stream().toList();
    }

    public void putPubliclySubsidy(Plan plan, int amount) {
        this.publiclySubsidies.put(plan.getId(), new PubliclySubsidy(this, plan, amount));
    }


}
