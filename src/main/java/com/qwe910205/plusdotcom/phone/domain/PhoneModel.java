package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.*;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode(of = {"phoneModelId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PhoneModel {

    @Id @GeneratedValue
    private Long id;

    @AttributeOverride(name = "value", column = @Column(name = "PHONE_MODEL_ID", unique = true, nullable = false, updatable = false))
    @Embedded
    private PhoneModelId phoneModelId;

    @AttributeOverride(name = "value", column = @Column(name = "NAME", unique = true, nullable = false))
    @Embedded
    private PhoneModelName name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private NetworkTech networkTech;

    @ElementCollection
    private final Set<Hashtag> hashtags = new HashSet<>();

    @AttributeOverride(name = "url", column = @Column(name = "THUMBNAIL"))
    @Embedded
    private ImageSource thumbnail;

    @OrderColumn
    @ElementCollection
    private final List<ImageSource> descriptionImages = new ArrayList<>();

    @MapKey(name = "colorName")
    @OneToMany(mappedBy = "phoneModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Map<ColorName, PhoneProduct> productMap = new HashMap<>();

    @AttributeOverride(name = "value", column = @Column(name = "SCREEN_SIZE"))
    @Embedded
    private ScreenSize screenSize;

    @AttributeOverride(name = "value", column = @Column(name = "RAM_CAPACITY"))
    @Embedded
    private MemoryCapacity ramCapacity;

    @AttributeOverride(name = "value", column = @Column(name = "ROM_CAPACITY"))
    @Embedded
    private MemoryCapacity romCapacity;

    @AttributeOverride(name = "value", column = @Column(name = "BATTERY_CAPACITY"))
    @Embedded
    private BatteryCapacity batteryCapacity;

    @Embedded
    private PhoneDescription description;

    @AttributeOverride(name = "value", column = @Column(name = "PRICE", nullable = false))
    @Embedded
    private Money money;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @ManyToMany
    private final Set<ConvenienceFunction> convenienceFunctions = new HashSet<>();

    @MapKey(name = "plan")
    @OneToMany(mappedBy = "phoneModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Map<Plan, PubliclySubsidy> publiclySubsidies = new HashMap<>();

    @Builder
    protected PhoneModel(String id, String name, String manufacturer, String networkTech, int price) {
        Objects.requireNonNull(id, "스마트폰 모델의 아이디는 필수입니다.");
        Objects.requireNonNull(name, "스마트폰 모델명은 필수입니다.");
        Objects.requireNonNull(manufacturer, "스마트폰 모델의 제조사는 필수입니다.");
        Objects.requireNonNull(networkTech, "스마트폰 모델의 통신 기술은 필수입니다.");
        this.phoneModelId = new PhoneModelId(id);
        this.name = new PhoneModelName(name);
        this.manufacturer = new Manufacturer(manufacturer);
        this.networkTech = new NetworkTech(networkTech);
        this.money = new Money(price);
    }

    public Long getId() {
        return this.id;
    }

    public String getPhoneModelId() {
        if (Objects.isNull(phoneModelId))
            return null;
        return phoneModelId.getValue();
    }

    public String getName() {
        if (Objects.isNull(name))
            return null;
        return name.getValue();
    }

    public String getManufacturer() {
        if (Objects.isNull(manufacturer))
            return null;
        return manufacturer.getName();
    }

    public String getNetworkTech() {
        if (Objects.isNull(networkTech))
            return null;
        return networkTech.getName();
    }

    public void addHashTags(List<String> hashtags) {
        this.hashtags.addAll(hashtags.stream().map(Hashtag::new).toList());
    }

    public List<String> getHashtags() {
        return hashtags.stream().map(Hashtag::getValue).toList();
    }

    public void setDescription(PhoneDescription description) {
        this.description = description;
    }

    public void addDescriptionImages(List<String> descriptionImages) {
        this.descriptionImages.addAll(descriptionImages.stream().map(ImageSource::new).toList());
    }

    public List<String> getDescriptionImages() {
        return descriptionImages.stream().map(ImageSource::getUrl).toList();
    }

    public void addProduct(String colorName, String colorCode, List<String> images, int stock) {
        PhoneProduct phoneProduct = new PhoneProduct(this, colorName, colorCode, images, stock);
        this.productMap.put(new ColorName(colorName), phoneProduct);
    }

    public Double getScreenSize() {
        if (Objects.isNull(screenSize))
            return null;
        return screenSize.getValue();
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = new ScreenSize(screenSize);
    }

    public Integer getRamCapacity() {
        if (Objects.isNull(ramCapacity))
            return null;
        return ramCapacity.getValue();
    }

    public void setRamCapacity(int capacity) {
        this.ramCapacity = new MemoryCapacity(capacity);
    }

    public Integer getRomCapacity() {
        if (Objects.isNull(romCapacity))
            return null;
        return romCapacity.getValue();
    }

    public void setRomCapacity(int capacity) {
        this.romCapacity = new MemoryCapacity(capacity);
    }

    public Integer getBatteryCapacity() {
        if (Objects.isNull(batteryCapacity))
            return null;
        return batteryCapacity.getValue();
    }

    public void setBatteryCapacity(int capacity) {
        this.batteryCapacity = new BatteryCapacity(capacity);
    }

    public Integer getMoney() {
        if (Objects.isNull(money))
            return null;
        return money.getValue();
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getConvenienceFunctions() {
        return convenienceFunctions.stream().map(ConvenienceFunction::getName).toList();
    }

    public void addConvenienceFunctions(List<String> convenienceFunctions) {
        this.convenienceFunctions.addAll(convenienceFunctions.stream().map(ConvenienceFunction::new).toList());
    }

    public String getThumbnail() {
        if (Objects.isNull(thumbnail))
            return null;
        return thumbnail.getUrl();
    }
    public void setThumbnail(String url) {
        this.thumbnail = new ImageSource(url);
    }

    public PhoneDescription getDescription() {
        return description;
    }

    public List<PhoneProduct> getAllProducts() {
        return productMap.values().stream().toList();
    }

    public int getPubliclySubsidy(Plan plan) {
        if (!publiclySubsidies.containsKey(plan))
            throw new NoSuchElementException("등록되지 않은 요금제입니다.");
        PubliclySubsidy publiclySubsidy = publiclySubsidies.get(plan);
        return publiclySubsidy.getAmount();
    }

    public void putPubliclySubsidy(Plan plan, int amount) {
        if (!this.getNetworkTech().equals(plan.getNetworkTech()))
            throw new IllegalArgumentException("스마트폰 모델과 요금제의 통신기술이 다르면 공시지원금을 추가할 수 없습니다.");
        publiclySubsidies.put(plan, new PubliclySubsidy(this, plan, amount));
    }
}
