package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.repository.PhoneRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PhoneInitServiceTest {

    @Autowired PhoneInitService phoneInitService;
    @Autowired ConvenienceFunctionInitService convenienceFunctionInitService;
    @Autowired ManufacturerInitService manufacturerInitService;
    @Autowired NetworkTechInitService networkTechInitService;
    @Autowired
    PhoneRepository phoneRepository;
    @PersistenceContext
    EntityManager em;

    @ParameterizedTest
    @MethodSource
    @DisplayName("Json 파일을 통신기술에 맞는 스마트폰 모델로 변환할 수 있다.")
    void convertJsonFileToPhoneModel(String resource, String networkTechName) {
        Map<String, PhoneModel> phoneModels = phoneInitService.convertJsonToPhoneModels(resource, networkTechName);

        assertThat(phoneModels.values().stream().map(PhoneModel::getNetworkTech).toList())
                .allMatch(networkTech -> networkTech.equals(networkTechName));
    }

    private static Stream<Arguments> convertJsonFileToPhoneModel() {
        return Stream.of(
            Arguments.of("data/5g-products.json", "5G"),
            Arguments.of("data/4g-products.json", "LTE")
        );
    }

    @Test
    @DisplayName("스마트폰 모델들의 크기를 한 번에 설정할 수 있다.")
    void setPhoneSizeAtOnce() {
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        assertThat(phoneModels.stream().map(PhoneModel::getSize).toList()).allMatch(Objects::isNull);
        assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getSizeDescription())
                .toList()).allMatch(Objects::isNull);

        phoneInitService.initSize(phoneModelMap);

        assertThat(phoneModels.stream().map(PhoneModel::getSize).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getSizeDescription())
                .toList()).allMatch(Objects::nonNull);
    }

    @Test
    @DisplayName("스마트폰 모델들의 무게를 한 번에 설정할 수 있다.")
    void setPhoneWeightAtOnce() {
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        assertThat(phoneModels.stream().map(PhoneModel::getWeight).toList()).allMatch(Objects::isNull);

        phoneInitService.initWeight(phoneModelMap);

        assertThat(phoneModels.stream().map(PhoneModel::getWeight).toList()).allMatch(Objects::nonNull);
    }

    @Test
    @DisplayName("스마트폰 모델들의 여러 정보들을 한 번에 설정할 수 있다.")
    void setPhoneInfosAtOnce() {
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        assertThat(phoneModels.stream().map(PhoneModel::getBatteryCapacity).toList()).allMatch(Objects::isNull);
        assertThat(phoneModels.stream().map(PhoneModel::getMemoryCapacity).toList()).allMatch(Objects::isNull);
        assertThat(phoneModels.stream().map(PhoneModel::getScreenSize).toList()).allMatch(Objects::isNull);
        assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getMemoryDescription()).toList()).allMatch(Objects::isNull);

        phoneInitService.initInfo(phoneModelMap);

        assertThat(phoneModels.stream().map(PhoneModel::getBatteryCapacity).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(PhoneModel::getMemoryCapacity).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(PhoneModel::getScreenSize).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getMemoryDescription()).toList()).allMatch(Objects::nonNull);
    }

    @Test
    @DisplayName("스마트폰 모델들의 출시일을 한 번에 설정할 수 있다.")
    void setReleaseDateAtOnce() {
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        assertThat(phoneModels.stream().map(PhoneModel::getReleaseDate).toList()).allMatch(Objects::isNull);

        phoneInitService.initReleaseDate(phoneModelMap);

        assertThat(phoneModels.stream().map(PhoneModel::getReleaseDate).toList()).allMatch(Objects::nonNull);
    }

    @Test
    @DisplayName("스마트폰 모델들의 편의기능을 한 번에 설정할 수 있다.")
    void setPhoneFunctionsAtOnce() {
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        assertThat(phoneModels.stream().map(PhoneModel::getConvenienceFunctions).toList())
                .allMatch(functions -> functions.size() == 0);

        phoneInitService.initConvenienceFunction(phoneModelMap);

        assertThat(phoneModels.stream().map(PhoneModel::getConvenienceFunctions).toList())
                .anyMatch(functions -> functions.size() > 0);
    }

    @Test
    @DisplayName("스마트폰 모델들의 썸네일을 한 번에 설정할 수 있다.")
    void setThumbnailAtOnce() {
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        assertThat(phoneModels.stream().map(PhoneModel::getThumbnail).toList()).allMatch(Objects::isNull);

        phoneInitService.initThumbnail(phoneModelMap);

        assertThat(phoneModels.stream().map(PhoneModel::getThumbnail).toList()).allMatch(Objects::nonNull);
    }

    @Transactional
    @Test
    @DisplayName("모든 스마트폰 모델 관련 데이터를 초기화할 수 있다.")
    void initAllDataAboutPhoneModel() {
        convenienceFunctionInitService.init();
        manufacturerInitService.init();
        networkTechInitService.init();
        em.flush();
        em.clear();

        phoneInitService.init();
        em.flush();
        em.clear();

        List<PhoneModel> phoneModels = phoneRepository.findAll();
        assertThat(phoneModels.stream().map(PhoneModel::getSize).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getSizeDescription())
                .toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(PhoneModel::getWeight).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(PhoneModel::getBatteryCapacity).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(PhoneModel::getMemoryCapacity).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(PhoneModel::getScreenSize).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getMemoryDescription()).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(PhoneModel::getReleaseDate).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(PhoneModel::getConvenienceFunctions).toList())
                .anyMatch(functions -> functions.size() > 0);
        assertThat(phoneModels.stream().map(PhoneModel::getThumbnail).toList()).allMatch(Objects::nonNull);
        assertThat(phoneModels.stream().map(PhoneModel::getAllProducts).toList()).allMatch(products -> products.size() > 0);
    }

    private Map<String, PhoneModel> createPhoneModelMap() {
        String resource1 = "data/5g-products.json";
        String resource2 = "data/4g-products.json";
        Map<String, PhoneModel> phoneModels = phoneInitService.convertJsonToPhoneModels(resource1, "5G");
        phoneModels.putAll(phoneInitService.convertJsonToPhoneModels(resource2, "LTE"));
        phoneInitService.deletePhonesForInit(phoneModels);
        return phoneModels;
    }

}