package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qwe910205.plusdotcom.datainit.service.dto.ColorDto;
import com.qwe910205.plusdotcom.datainit.service.dto.PhoneModelDto;
import com.qwe910205.plusdotcom.datainit.service.dto.PhoneModelDtoList;
import com.qwe910205.plusdotcom.phone.domain.PhoneDescription;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.factory.PhoneModelFactory;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Component
public class PhoneInitializer implements DataInitializer {

    private int priority = 1;

    private final PhoneRepository phoneRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void init() {
        Map<String, PhoneModel> phoneModels = convertJsonToPhoneModels("data/5g-products.json", "5G");
        phoneModels.putAll(convertJsonToPhoneModels("data/4g-products.json", "LTE"));
        deletePhonesForInit(phoneModels);
        initSize(phoneModels);
        initInfo(phoneModels);
        initReleaseDate(phoneModels);
        initConvenienceFunction(phoneModels);
        initThumbnail(phoneModels);
        phoneRepository.saveAll(phoneModels.values());
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(DataInitializer o) {
        return this.priority - o.getPriority();
    }

    public Map<String, PhoneModel> convertJsonToPhoneModels(String resource, String networkTechName) {
        PhoneModelDtoList phoneModelDtoList;
        try {
            phoneModelDtoList = objectMapper.readValue(new ClassPathResource(resource).getFile(), PhoneModelDtoList.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, PhoneModel> phoneModels = new HashMap<>();
        for (PhoneModelDto product : phoneModelDtoList.products()) {
            PhoneModel phoneModel = PhoneModelFactory.create(product.model_id(), product.name(), product.manufacturer(), networkTechName, product.price());
            phoneModel.addHashTags(Arrays.stream(product.hash_tag().split(" ")).toList());
            PhoneDescription description = PhoneDescription.builder().cpuDescription(product.CPU()).displayDescription(product.display_description())
                    .cameraDescription(product.camera()).batteryDescription(product.battery_capacity()).waterproofDescription(product.waterproof())
                    .build();
            phoneModel.setDescription(description);
            phoneModel.addDescriptionImages(product.detail_image());
            for (ColorDto colorDto : product.color()) {
                phoneModel.addProduct(colorDto.color_name(), colorDto.color_code(), colorDto.color_image(), 100);
            }
            phoneModels.put(phoneModel.getName(), phoneModel);
        }
        return phoneModels;
    }

    public void deletePhonesForInit(Map<String, PhoneModel> phoneModelMap) {
        phoneModelMap.remove("갤럭시 A21s");                // 재고가 없어서 정보를 볼 수 없어서
        phoneModelMap.remove("갤럭시 A31");                 // 재고가 없어서 정보를 볼 수 없어서
        phoneModelMap.remove("갤럭시 S20 FE 5G");           // 재고가 없어서 정보를 볼 수 없어서
        phoneModelMap.remove("갤럭시 노트20");               // 재고가 없어서 정보를 볼 수 없어서
        phoneModelMap.remove("iPhone 13 Pro Max 256G");    // 갑자기 사라져서
        phoneModelMap.remove("iPhone 13 Pro Max 128G");    // 갑자기 사라져서
        phoneModelMap.remove("Galaxy Buddy 2");            // 공시지원금을 구할 수 없어서
        phoneModelMap.remove("Redmi Note 11");             // 공시지원금을 구할 수 없어서
        phoneModelMap.remove("U+키즈폰 with 리틀카카오프렌즈");// 공시지원금을 구할 수 없어서
        phoneModelMap.remove("갤럭시 A33 5G");              // 공시지원금을 구할 수 없어서
    }

    public void initSize(Map<String, PhoneModel> phoneModelMap) {
        setSize(phoneModelMap, "갤럭시 Z Flip 4", "접었을때 : 155.1 x 67.1 x 14.2 / 15.8mm , 펼쳤을때 : 155.1 x 130.1 x 6.3mm");
        setSize(phoneModelMap, "갤럭시 Z Flip 4 메종키츠네 에디션", "접었을 때 : 84.8 X 71.9 X 15.9/17.1 mm , 펼쳤을 때 : 165.2 X 71.9 X 6.9 mm");
        setSize(phoneModelMap, "갤럭시 Z Flip 4 512GB", "접었을 때 : 84.8 X 71.9 X 15.9/17.1 mm , 펼쳤을 때 : 165.2 X 71.9 X 6.9 mm");
        setSize(phoneModelMap, "갤럭시 Z Fold 4", "접었을때 : 155.1 x 67.1 x 14.2 / 15.8mm , 펼쳤을때 : 155.1 x 130.1 x 6.3mm");
        setSize(phoneModelMap, "갤럭시 Z Fold 4 512GB", "접었을때 : 155.1 x 67.1 x 14.2 / 15.8mm , 펼쳤을때 : 155.1 x 130.1 x 6.3mm");
        setSize(phoneModelMap, "갤럭시 S20 중고폰", "151.7 x 69.1 x 7.9 mm");
        setSize(phoneModelMap, "갤럭시 S20+ 중고폰", "161.9 x 73.7 x 7.8 mm");
        setSize(phoneModelMap, "갤럭시 S20 Ultra 중고폰", "166.9 x 76 x 8.8 mm");
        setSize(phoneModelMap, "갤럭시 A33 5G", "159.7 x 74.0 x 8.1mm");
        setSize(phoneModelMap, "Galaxy Buddy 2", "77 X 165.5 X 8.4 mm");
        setSize(phoneModelMap, "Galaxy A53 5G", "159.6 x 74.8 x 8.1mm");
        setSize(phoneModelMap, "iPhone SE 128GB 3세대",  "138.4 x 67.3 x 7.3 mm");
        setSize(phoneModelMap, "iPhone SE 64GB 3세대", "138.4 x 67.3 x 7.3 mm");
        setSize(phoneModelMap, "갤럭시 S22", "70.6 x 146.0 x 7.6 mm");
        setSize(phoneModelMap, "갤럭시 S22+", "75.8 x 157.4 x 7.6 mm");
        setSize(phoneModelMap, "갤럭시 S22 Ultra", "77.9 x 163.3 x 8.9 mm");
        setSize(phoneModelMap, "iPhone 13 mini 128G",  "131.5 x 64.2 x 7.65 mm");
        setSize(phoneModelMap, "iPhone 13 128G",  "146.7 x 71.5 x 7.65 mm");
        setSize(phoneModelMap, "iPhone 13 Pro 128G", "146.7 x 71.5 x 7.65 mm");
        setSize(phoneModelMap, "iPhone 13 Pro 256G", "160.8 x 78.1 x 7.65 mm");
        setSize(phoneModelMap, "iPhone 13 Pro Max 128G", "160.8 x 78.1 x 7.65 mm");
        setSize(phoneModelMap, "iPhone 13 Pro Max 256G", "160.8 x 78.1 x 7.65 mm");
        setSize(phoneModelMap, "Galaxy Buddy", "167.2 x 76.4 x 9mm");
        setSize(phoneModelMap, "갤럭시 Z 플립3", "접었을 때 86.4 x 72.2 x 15.9/17.1 mm, 펼쳤을 때 166 x 72.2 x 6.9 mm");
        setSize(phoneModelMap, "갤럭시 S21", "71.2 x 151.7 x 7.9 mm");
        setSize(phoneModelMap, "iPhone 12 mini 128G", "131.5 x 64.2 x 7.4 mm");
        setSize(phoneModelMap, "iPhone 12 Pro Max 256G", "160.8 x 78.1 x 7.4 mm");
        setSize(phoneModelMap, "iPhone 12 128G", "146.7 x 71.5 x 7.4 mm");
        setSize(phoneModelMap, "iPhone 12 64G", "146.7 x 71.5 x 7.4 mm");
        setSize(phoneModelMap, "iPhone 12 Pro 256G", "146.7 x 71.5 x 7.4 mm");
        setSize(phoneModelMap, "갤럭시 S20 FE 5G", "159.8 x 74.5 x 8.4 mm");
        setSize(phoneModelMap, "갤럭시 노트20", "161.6 x 75.2 x 8.3 mm");
        setSize(phoneModelMap, "Galaxy A13", "165.1 x 76.4 X 8.8 mm");
        setSize(phoneModelMap, "Redmi Note 11", "159.9*73.9*8.1mm");
        setSize(phoneModelMap, "갤럭시 A23", "165.4×77.0×8.4 mm");
        setSize(phoneModelMap, "U+키즈폰 with 리틀카카오프렌즈", "147.1 x 71.6 x 9.2mm");
        setSize(phoneModelMap, "갤럭시 폴더2 2021", "122 X 60.2 X 16.1mm");
        setSize(phoneModelMap, "갤럭시 A12", "164.0 x 75.8 x 8.9 mm");
        setSize(phoneModelMap, "갤럭시 A21s",  "163.7 x 75.3 x 8.9 mm");
        setSize(phoneModelMap, "갤럭시 A31", "159.3 × 73.1 × 8.6 ㎜");
    }

    private void setSize(Map<String, PhoneModel> phoneModelMap, String name, String description) {
        PhoneModel phoneModel = phoneModelMap.get(name);
        if (Objects.isNull(phoneModel)) return;
        PhoneDescription phoneDescription = phoneModel.getDescription();
        PhoneDescription changedPhoneDescription = phoneDescription.changedDescription(null, null, description, null, null, null, null);
        phoneModel.setDescription(changedPhoneDescription);
    }

    public void initInfo(Map<String, PhoneModel> phoneModelMap) {
        setInfo(phoneModelMap, "갤럭시 Z Flip 4", 4400, 7.6, "RAM 12GB, ROM 256GB", 12, 256);
        setInfo(phoneModelMap, "갤럭시 Z Flip 4 메종키츠네 에디션", 3700, 6.7, "RAM 8GB, ROM 256GB", 8, 256);
        setInfo(phoneModelMap, "갤럭시 Z Flip 4 512GB", 3700, 6.7, "RAM 8GB, ROM 512GB", 8, 512);
        setInfo(phoneModelMap, "갤럭시 Z Fold 4", 4400, 7.6, "RAM 12GB, ROM 256GB", 12, 256);
        setInfo(phoneModelMap, "갤럭시 Z Fold 4 512GB", 4400, 7.6, "RAM 12GB, ROM 512GB", 12, 512);
        setInfo(phoneModelMap, "갤럭시 S20 중고폰", 4000, 6.2, "12GB (RAM) + 128GB(ROM) micro SD up to 1TB", 12, 128);
        setInfo(phoneModelMap, "갤럭시 S20+ 중고폰", 4500, 6.7, "12GB (RAM) + 256GB(ROM) micro SD up to 1TB", 12, 256);
        setInfo(phoneModelMap, "갤럭시 S20 Ultra 중고폰", 5000, 6.9, "12GB (RAM) + 256GB(ROM) micro SD up to 1TB", 12, 256);
        setInfo(phoneModelMap, "갤럭시 A33 5G", 5000, 6.4, "RAM 6GB + 내장 메모리 128GB (외장 메모리 슬롯 지원 최대 1TB (별매)", 6, 128);
        setInfo(phoneModelMap, "Galaxy Buddy 2", 5000, 6.6, "RAM : 4GB, ROM : 128GB", 4, 128);
        setInfo(phoneModelMap, "Galaxy A53 5G", 5000, 6.5, "RAM 6GB, ROM 128GB (외장 메모리 슬롯 지원 최대 1TB)", 6, 128);
        setInfo(phoneModelMap, "iPhone SE 128GB 3세대", 2018, 4.7, "128GB", 4, 128);
        setInfo(phoneModelMap, "iPhone SE 64GB 3세대", 2018, 4.7, "64GB", 4, 64);
        setInfo(phoneModelMap, "갤럭시 S22", 3700, 6.1, "8GB RAM / 256GB", 8, 256);
        setInfo(phoneModelMap, "갤럭시 S22+", 4500, 6.6, "8GB RAM / 256GB", 8, 256);
        setInfo(phoneModelMap, "갤럭시 S22 Ultra", 5000, 6.8, "12GB RAM / 256GB", 12, 256);
        setInfo(phoneModelMap, "iPhone 13 mini 128G", 2406, 5.4, "128GB", 4, 128);
        setInfo(phoneModelMap, "iPhone 13 128G", 3227, 6.1, "128GB", 4, 128);
        setInfo(phoneModelMap, "iPhone 13 Pro 128G", 3095, 6.1, "128GB", 6, 128);
        setInfo(phoneModelMap, "iPhone 13 Pro 256G", 3095, 6.1, "256GB", 6, 256);
//        setInfo(phoneModelMap, "iPhone 13 Pro Max 128G", 4352, );    갑자기 사라짐
//        setInfo(phoneModelMap, "iPhone 13 Pro Max 256G", 4352);      갑자기 사라짐
        setInfo(phoneModelMap, "Galaxy Buddy", 5000, 6.6, "4GB RAM / 128GB ROM", 4, 128);
        setInfo(phoneModelMap, "갤럭시 Z 플립3", 3300, 6.7, "RAM 8GB, ROM 256GB", 8, 256);
        setInfo(phoneModelMap, "갤럭시 S21", 4000, 6.2, "8GB RAM / 256GB ROM", 8, 256);
        setInfo(phoneModelMap, "iPhone 12 mini 128G", 2227, 5.4, "128GB", 4, 128);
        setInfo(phoneModelMap, "iPhone 12 Pro Max 256G", 3687, 6.7, "256GB", 6, 256);
        setInfo(phoneModelMap, "iPhone 12 128G", 2815, 6.1, "128GB", 4, 128);
        setInfo(phoneModelMap, "iPhone 12 64G", 2815, 6.1, "64GB", 4, 64);
        setInfo(phoneModelMap, "iPhone 12 Pro 256G", 2815, 6.1, "256GB", 6, 256);
        setInfo(phoneModelMap, "갤럭시 S20 FE 5G", 4500, 6.5, "6GB RAM / 128GB ROM", 6, 128);
        setInfo(phoneModelMap, "갤럭시 노트20", 4300, 6.7, "8GB RAM / 256GB ROM", 8, 256);
        setInfo(phoneModelMap, "Galaxy A13", 5000, 6.6, "RAM 4GB, ROM 64GB *외장 메모리 슬롯 최대 1TB(별매)", 4, 64);
        setInfo(phoneModelMap, "Redmi Note 11", 5000, 6.43, "6GB+128GB", 6, 128);
        setInfo(phoneModelMap, "갤럭시 A23", 5000, 6.6, "128 GB + 4 GB", 4, 128);
        setInfo(phoneModelMap, "U+키즈폰 with 리틀카카오프렌즈", 3000, 5.3, "4GB RAM / 64GB ROM", 4, 64);
        setInfo(phoneModelMap, "갤럭시 폴더2 2021", 1950, 3.8, "3GB RAM / 32GB ROM", 3, 32);
        setInfo(phoneModelMap, "갤럭시 A12", 5000, 6.5, "3GB RAM / 32GB ROM, 외장 메모리 슬롯 지원", 3, 32);
        setInfo(phoneModelMap, "갤럭시 A21s", 5000, 6.5, "RAM 3 GB + 내장 메모리 32 GB", 3, 32);
        setInfo(phoneModelMap, "갤럭시 A31", 5000, 6.4, "4GB RAM + 64GB ROM micro SD 슬롯지원 (최대 512GB)", 4, 64);
    }

    private void setInfo(Map<String, PhoneModel> phoneModelMap, String name, int batteryCapacity, double screenSize, String memoryDescription, int ramCapacity, int romCapacity) {
        PhoneModel phoneModel = phoneModelMap.get(name);
        if (Objects.isNull(phoneModel)) return;
        phoneModel.setBatteryCapacity(batteryCapacity);
        phoneModel.setScreenSize(screenSize);
        phoneModel.setRamCapacity(ramCapacity);
        phoneModel.setRomCapacity(romCapacity);
        PhoneDescription phoneDescription = phoneModel.getDescription().changedDescription(null, null, null, null, memoryDescription, null, null);
        phoneModel.setDescription(phoneDescription);
    }

    public void initReleaseDate(Map<String, PhoneModel> phoneModelMap) {
        setReleaseDate(phoneModelMap, "갤럭시 Z Flip 4", LocalDate.of(2022, 8, 26));
        setReleaseDate(phoneModelMap, "갤럭시 Z Flip 4 메종키츠네 에디션", LocalDate.of(2022, 8, 26));
        setReleaseDate(phoneModelMap, "갤럭시 Z Flip 4 512GB", LocalDate.of(2022, 8, 26));
        setReleaseDate(phoneModelMap, "갤럭시 Z Fold 4", LocalDate.of(2022, 8, 26));
        setReleaseDate(phoneModelMap, "갤럭시 Z Fold 4 512GB", LocalDate.of(2022, 8, 26));
        setReleaseDate(phoneModelMap, "갤럭시 S20 중고폰", LocalDate.of(2020, 3, 6));
        setReleaseDate(phoneModelMap, "갤럭시 S20+ 중고폰", LocalDate.of(2020, 3, 6));
        setReleaseDate(phoneModelMap, "갤럭시 S20 Ultra 중고폰", LocalDate.of(2020, 3, 6));
        setReleaseDate(phoneModelMap, "갤럭시 A33 5G", LocalDate.of(2022, 7, 29));
        setReleaseDate(phoneModelMap, "Galaxy Buddy 2", LocalDate.of(2021, 9, 10));
        setReleaseDate(phoneModelMap, "Galaxy A53 5G", LocalDate.of(2022, 3, 18));
        setReleaseDate(phoneModelMap, "iPhone SE 128GB 3세대", LocalDate.of(2022, 3, 8));
        setReleaseDate(phoneModelMap, "iPhone SE 64GB 3세대", LocalDate.of(2022, 3, 8));
        setReleaseDate(phoneModelMap, "갤럭시 S22", LocalDate.of(2022, 2, 25));
        setReleaseDate(phoneModelMap, "갤럭시 S22+", LocalDate.of(2022, 2, 25));
        setReleaseDate(phoneModelMap, "갤럭시 S22 Ultra", LocalDate.of(2022, 2, 25));
        setReleaseDate(phoneModelMap, "iPhone 13 mini 128G", LocalDate.of(2021, 9, 24));
        setReleaseDate(phoneModelMap, "iPhone 13 128G", LocalDate.of(2021, 9, 24));
        setReleaseDate(phoneModelMap, "iPhone 13 Pro 128G", LocalDate.of(2021, 9, 24));
        setReleaseDate(phoneModelMap, "iPhone 13 Pro 256G", LocalDate.of(2021, 9, 24));
        setReleaseDate(phoneModelMap, "iPhone 13 Pro Max 128G", LocalDate.of(2021, 9, 24));
        setReleaseDate(phoneModelMap, "iPhone 13 Pro Max 256G", LocalDate.of(2021, 9, 24));
        setReleaseDate(phoneModelMap, "Galaxy Buddy", LocalDate.of(2021, 9, 10));
        setReleaseDate(phoneModelMap, "갤럭시 Z 플립3", LocalDate.of(2021, 8, 27));
        setReleaseDate(phoneModelMap, "갤럭시 S21", LocalDate.of(2021, 1, 29));
        setReleaseDate(phoneModelMap, "iPhone 12 mini 128G", LocalDate.of(2020, 11, 13));
        setReleaseDate(phoneModelMap, "iPhone 12 Pro Max 256G", LocalDate.of(2020, 11, 13));
        setReleaseDate(phoneModelMap, "iPhone 12 128G", LocalDate.of(2020, 11, 13));
        setReleaseDate(phoneModelMap, "iPhone 12 64G", LocalDate.of(2020, 11, 13));
        setReleaseDate(phoneModelMap, "iPhone 12 Pro 256G", LocalDate.of(2020, 11, 13));
        setReleaseDate(phoneModelMap, "갤럭시 S20 FE 5G", LocalDate.of(2020, 10, 16));
        setReleaseDate(phoneModelMap, "갤럭시 노트20", LocalDate.of(2020, 8, 21));
        setReleaseDate(phoneModelMap, "Galaxy A13", LocalDate.of(2021, 12, 3));
        setReleaseDate(phoneModelMap, "Redmi Note 11", LocalDate.of(2022, 2, 9));
        setReleaseDate(phoneModelMap, "갤럭시 A23", LocalDate.of(2022, 3, 25));
        setReleaseDate(phoneModelMap, "U+키즈폰 with 리틀카카오프렌즈", LocalDate.of(2022, 1, 14));
        setReleaseDate(phoneModelMap, "갤럭시 폴더2 2021", LocalDate.of(2021, 4, 3));
        setReleaseDate(phoneModelMap, "갤럭시 A12", LocalDate.of(2021, 2, 9));
        setReleaseDate(phoneModelMap, "갤럭시 A21s", LocalDate.of(2020, 7, 24));
        setReleaseDate(phoneModelMap, "갤럭시 A31", LocalDate.of(2020, 5, 7));
    }

    private void setReleaseDate(Map<String, PhoneModel> phoneModelMap, String name, LocalDate releaseDate) {
        PhoneModel phoneModel = phoneModelMap.get(name);
        if (Objects.isNull(phoneModel)) return;
        phoneModel.setReleaseDate(releaseDate);
    }

    public void initConvenienceFunction(Map<String, PhoneModel> phoneModelMap) {
        final String FINGERPRINT_RECOGNITION = "지문인식";
        final String SAMSUNG_PAY = "삼성페이";
        final String FACE_RECOGNITION = "안면인식";
        setConvenienceFunctions(phoneModelMap, "갤럭시 Z Flip 4", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 Z Flip 4 메종키츠네 에디션", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 Z Flip 4 512GB", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 Z Fold 4", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 Z Fold 4 512GB", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 S20 중고폰", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 S20+ 중고폰", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 S20 Ultra 중고폰", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 A33 5G", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "Galaxy Buddy 2", FINGERPRINT_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "Galaxy A53 5G", FINGERPRINT_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone SE 128GB 3세대", FINGERPRINT_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone SE 64GB 3세대", FINGERPRINT_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "갤럭시 S22", FINGERPRINT_RECOGNITION, FACE_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 S22+", FINGERPRINT_RECOGNITION, FACE_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 S22 Ultra", FINGERPRINT_RECOGNITION, FACE_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "iPhone 13 mini 128G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone 13 128G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone 13 Pro 128G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone 13 Pro 256G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone 13 Pro Max 128G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone 13 Pro Max 256G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "Galaxy Buddy", FINGERPRINT_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "갤럭시 Z 플립3", FINGERPRINT_RECOGNITION, FACE_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 S21", FINGERPRINT_RECOGNITION, FACE_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "iPhone 12 mini 128G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone 12 Pro Max 256G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone 12 128G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone 12 64G", FINGERPRINT_RECOGNITION, FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "iPhone 12 Pro 256G", FACE_RECOGNITION);
        setConvenienceFunctions(phoneModelMap, "갤럭시 S20 FE 5G", FINGERPRINT_RECOGNITION, SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 노트20", FINGERPRINT_RECOGNITION, FACE_RECOGNITION, SAMSUNG_PAY);
//        setConvenienceFunctions(phoneModelMap, "Galaxy A13", );      아무것도 없음
//        setConvenienceFunctions(phoneModelMap, "Redmi Note 11",);    아무것도 없음
        setConvenienceFunctions(phoneModelMap, "갤럭시 A23", SAMSUNG_PAY);
//        setConvenienceFunctions(phoneModelMap, "U+키즈폰 with 리틀카카오프렌즈", );
//        setConvenienceFunctions(phoneModelMap, "갤럭시 폴더2 2021",);  아무것도 없음
//        setConvenienceFunctions(phoneModelMap, "갤럭시 A12",);        아무것도 없음
        setConvenienceFunctions(phoneModelMap, "갤럭시 A21s", SAMSUNG_PAY);
        setConvenienceFunctions(phoneModelMap, "갤럭시 A31", SAMSUNG_PAY);
    }

    private void setConvenienceFunctions(Map<String, PhoneModel> phoneModelMap, String name, String... convenienceFunctionNames) {
        PhoneModel phoneModel = phoneModelMap.get(name);
        if (Objects.isNull(phoneModel)) return;
        phoneModel.addConvenienceFunctions(Arrays.asList(convenienceFunctionNames));
    }

    public void initThumbnail(Map<String, PhoneModel> phoneModelMap) {
        setThumbnail(phoneModelMap, "갤럭시 Z Flip 4", "https://image.lguplus.com/static/pc-contents/images/prdv/20220812-021216-097-IrwyS2Zu.jpg");
        setThumbnail(phoneModelMap, "갤럭시 Z Flip 4 메종키츠네 에디션", "https://image.lguplus.com/static/pc-contents/images/prdv/20220812-030323-432-3D3x2A6z.jpg");
        setThumbnail(phoneModelMap, "갤럭시 Z Flip 4 512GB", "https://image.lguplus.com/static/pc-contents/images/prdv/20220812-025607-814-9q8rtAhk.jpg");
        setThumbnail(phoneModelMap, "갤럭시 Z Fold 4", "https://image.lguplus.com/static/pc-contents/images/prdv/20220812-011910-841-nv8AawYX.jpg");
        setThumbnail(phoneModelMap, "갤럭시 Z Fold 4 512GB", "https://image.lguplus.com/static/pc-contents/images/prdv/20220812-014716-003-xl3JSwFl.jpg");
        setThumbnail(phoneModelMap, "갤럭시 S20 중고폰", "https://image.lguplus.com/static/pc-contents/images/prdv/20220809-043619-441-2S7ywHeB.jpg");
        setThumbnail(phoneModelMap, "갤럭시 S20+ 중고폰", "https://image.lguplus.com/static/pc-contents/images/prdv/20220809-044505-923-4tt40TDV.jpg");
        setThumbnail(phoneModelMap, "갤럭시 S20 Ultra 중고폰", "https://image.lguplus.com/static/pc-contents/images/prdv/20220809-045336-720-vCkA0rwu.jpg");
        setThumbnail(phoneModelMap, "갤럭시 A33 5G", "https://image.lguplus.com/static/pc-contents/images/prdv/20220729-094817-735-tXO0PeyD.jpg");
        setThumbnail(phoneModelMap, "Galaxy Buddy 2", "https://image.lguplus.com/static/pc-contents/images/prdv/20220616-073051-526-l4VusvGl.jpg");
        setThumbnail(phoneModelMap, "Galaxy A53 5G", "https://image.lguplus.com/common/images/hphn/product/SM-A536N/list/ushop_SM-A536N_AB_A20220317160344524.jpg");
        setThumbnail(phoneModelMap, "iPhone SE 128GB 3세대", "https://image.lguplus.com/common/images/hphn/product/A2783-128/list/ushop_A2783-128_01_A20220317111140913.jpg");
        setThumbnail(phoneModelMap, "iPhone SE 64GB 3세대", "https://image.lguplus.com/common/images/hphn/product/A2783-64/list/ushop_A2783-64_67_A20220317110612311.jpg");
        setThumbnail(phoneModelMap, "갤럭시 S22", "https://image.lguplus.com/common/images/hphn/product/SM-S901N/list/ushop_SM-S901N_EW_A20220211190332898.jpg");
        setThumbnail(phoneModelMap, "갤럭시 S22+", "https://image.lguplus.com/common/images/hphn/product/SM-S906N/list/ushop_SM-S906N_EK_A20220211191710809.jpg");
        setThumbnail(phoneModelMap, "갤럭시 S22 Ultra", "https://image.lguplus.com/common/images/hphn/product/SM-S908N/list/ushop_SM-S908N_ER_A20220211193127146.jpg");
        setThumbnail(phoneModelMap, "iPhone 13 mini 128G", "https://image.lguplus.com/common/images/hphn/product/A2628-128/list/ushop_A2628-128_67_A20210928171215444.jpg");
        setThumbnail(phoneModelMap, "iPhone 13 128G", "https://image.lguplus.com/common/images/hphn/product/A2633-128/list/ushop_A2633-128_21_A20210928163457385.jpg");
        setThumbnail(phoneModelMap, "iPhone 13 Pro 128G", "https://image.lguplus.com/common/images/hphn/product/A2638-128/list/ushop_A2638-128_SU_A20210928152740918.jpg");
        setThumbnail(phoneModelMap, "iPhone 13 Pro 256G", "https://image.lguplus.com/common/images/hphn/product/A2638-256/list/ushop_A2638-256_19_A20210928153758362.jpg");
        setThumbnail(phoneModelMap, "iPhone 13 Pro Max 128G", "https://image.lguplus.com/common/images/hphn/product/A2643-128/list/ushop_A2643-128_SU_A20210928141754477.jpg");
//        setThumbnail(phoneModelMap, "iPhone 13 Pro Max 256G", "");
        setThumbnail(phoneModelMap, "Galaxy Buddy", "https://image.lguplus.com/common/images/hphn/product/SM-A226L/list/ushop_SM-A226L_AG_A20210910091755159.jpg");
        setThumbnail(phoneModelMap, "갤럭시 Z 플립3", "https://image.lguplus.com/common/images/hphn/product/SM-F711N/list/ushop_SM-F711N_EE_A.jpg20210813173012644.jpg");
        setThumbnail(phoneModelMap, "갤럭시 S21", "https://image.lguplus.com/common/images/hphn/product/SM-G991N/list/ushop_SM-G991N_EI_A2021072322505048220210729231217939.jpg");
        setThumbnail(phoneModelMap, "iPhone 12 mini 128G", "https://image.lguplus.com/common/images/hphn/product/A2399-128/list/ushop_A2399-128_C1_A2021042214003228420210729232227359.jpg");
        setThumbnail(phoneModelMap, "iPhone 12 Pro Max 256G", "https://image.lguplus.com/common/images/hphn/product/A2411-256/list/ushop_A2411-256_10_A2020111117472602620210729232940081.jpg");
        setThumbnail(phoneModelMap, "iPhone 12 128G", "https://image.lguplus.com/common/images/hphn/product/A2403-128/list/ushop_A2403-128_C1_A2021042214012213320210729231928933.jpg");
        setThumbnail(phoneModelMap, "iPhone 12 64G", "https://image.lguplus.com/common/images/hphn/product/A2403-64/list/ushop_A2403-64_20_A2020102209481712820210729232131934.jpg");
        setThumbnail(phoneModelMap, "iPhone 12 Pro 256G", "https://image.lguplus.com/common/images/hphn/product/A2407-256/list/ushop_A2407-256_GP_A2020102210470923120210729232647352.jpg");
        setThumbnail(phoneModelMap, "갤럭시 S20 FE 5G", "https://image.lguplus.com/common/images/hphn/product/SM-G781N/list/ushop_SM-G781N_AW_A2020100510514248720210729233904935.jpg");
        setThumbnail(phoneModelMap, "갤럭시 노트20", "https://image.lguplus.com/common/images/hphn/product/SM-N981N/list/ushop_SM-N981N_AI_A2020080421114703920210729234403731.jpg");
        setThumbnail(phoneModelMap, "Galaxy A13", "https://image.lguplus.com/static/pc-contents/images/prdv/20220722-100310-983-p0tPK0iK.jpg");
        setThumbnail(phoneModelMap, "Redmi Note 11", "https://image.lguplus.com/common/images/hphn/product/2201117TY/list/ushop_2201117TY_20_A20220406085806593.jpg");
        setThumbnail(phoneModelMap, "갤럭시 A23", "https://image.lguplus.com/common/images/hphn/product/SM-A235N/list/ushop_SM-A235N_01_A20220325093320937.jpg");
        setThumbnail(phoneModelMap, "U+키즈폰 with 리틀카카오프렌즈", "https://image.lguplus.com/common/images/hphn/product/SM-G525N-UK/list/ushop_SM-G525N-UK_67_A20220114091756967.jpg");
        setThumbnail(phoneModelMap, "갤럭시 폴더2 2021", "https://image.lguplus.com/common/images/hphn/product/SM-G160N2021/list/ushop_SM-G160N2021_80_A2021040209310101220210730093020319.jpg");
        setThumbnail(phoneModelMap, "갤럭시 A12", "https://image.lguplus.com/common/images/hphn/product/SM-A125N/list/ushop_SM-A125N_01_A2021020515181081820210730093109470.jpg");
        setThumbnail(phoneModelMap, "갤럭시 A21s", "https://image.lguplus.com/common/images/hphn/product/SM-A217N/list/ushop_SM-A217N_80_A20200717082139766.jpg");
        setThumbnail(phoneModelMap, "갤럭시 A31", "https://image.lguplus.com/common/images/hphn/product/SM-A315N/list/ushop_SM-A315N_01_A20200507081842673.jpg");
    }

    private void setThumbnail(Map<String, PhoneModel> phoneModelMap, String name, String thumbnailUrl) {
        PhoneModel phoneModel = phoneModelMap.get(name);
        if (Objects.isNull(phoneModel)) return;
        phoneModel.setThumbnail(thumbnailUrl);
    }
}
