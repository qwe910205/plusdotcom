package com.qwe910205.plusdotcom.datainit.service;

import com.qwe910205.plusdotcom.phonemodel.domain.PhoneModel;
import com.qwe910205.plusdotcom.phonemodel.repository.PhoneRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PhoneInitServiceTest {

    @Autowired PhoneInitService service;
    @Autowired ConvenienceFunctionInitService convenienceFunctionInitService;
    @Autowired ManufacturerInitService manufacturerInitService;
    @Autowired NetworkTechInitService networkTechInitService;
    @Autowired
    PhoneRepository repository;
    @PersistenceContext
    EntityManager em;

    @Test
    void Json파일을_스마트폰_모델로_변환할_수_있다() {
        // given
        String resource1 = "datas/5g-products.txt";
        String resource2 = "datas/4g-products.txt";

        // when
        Map<String, PhoneModel> phoneModels = service.convertJsonToPhoneModels(resource1, "5G");
        phoneModels.putAll(service.convertJsonToPhoneModels(resource2, "LTE"));

        // then
        Assertions.assertThat(phoneModels.size()).isGreaterThan(29);
    }

    @Test
    void 스마트폰_모델들의_크기를_한번에_초기화할_수_있다() {
        // given
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getSize).toList()).allMatch(Objects::isNull);
        Assertions.assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getSizeDescription())
                .toList()).allMatch(Objects::isNull);

        // when
        service.initSize(phoneModelMap);
        phoneModels = phoneModelMap.values();

        // then
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getSize).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getSizeDescription())
                .toList()).allMatch(Objects::nonNull);
    }

    @Test
    void 스마트폰_모델들의_무게를_한번에_초기화할_수_있다() {
        // given
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getWeight).toList()).allMatch(Objects::isNull);

        // when
        service.initWeight(phoneModelMap);
        phoneModels = phoneModelMap.values();

        // then
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getWeight).toList()).allMatch(Objects::nonNull);
    }

    @Test
    void 스마트폰_모델들의_여러_정보들을_한번에_초기화할_수_있다() {
        // given
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getBatteryCapacity).toList()).allMatch(Objects::isNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getMemoryCapacity).toList()).allMatch(Objects::isNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getScreenSize).toList()).allMatch(Objects::isNull);
        Assertions.assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getMemoryDescription()).toList()).allMatch(Objects::isNull);

        // when
        service.initInfo(phoneModelMap);
        phoneModels = phoneModelMap.values();

        // then
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getBatteryCapacity).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getMemoryCapacity).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getScreenSize).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getMemoryDescription()).toList()).allMatch(Objects::nonNull);
    }

    @Test
    void 스마트폰_모델들의_출시일을_한번에_초기화할_수_있다() {
        // given
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getReleaseDate).toList()).allMatch(Objects::isNull);

        // when
        service.initReleaseDate(phoneModelMap);
        phoneModels = phoneModelMap.values();

        // then
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getReleaseDate).toList()).allMatch(Objects::nonNull);
    }

    @Test
    void 스마트폰_모델들의_편의기능을_한번에_초기화할_수_있다() {
        // given
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getConvenienceFunctions).toList())
                .allMatch(functions -> functions.size() == 0);

        // when
        service.initConvenienceFunction(phoneModelMap);
        phoneModels = phoneModelMap.values();

        // then
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getConvenienceFunctions).toList())
                .anyMatch(functions -> functions.size() > 0);
    }

    @Test
    void 스마트폰_모델들의_썸네일을_한번에_초기화할_수_있다() {
        // given
        Map<String, PhoneModel> phoneModelMap = createPhoneModelMap();
        Collection<PhoneModel> phoneModels = phoneModelMap.values();
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getThumbnail).toList()).allMatch(Objects::isNull);

        // when
        service.initThumbnail(phoneModelMap);
        phoneModels = phoneModelMap.values();

        // then
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getThumbnail).toList()).allMatch(Objects::nonNull);
    }

    @Transactional
    @Test
    void 스마트폰_모델_데이터를_초기화할_수_있다() {
        // given
        convenienceFunctionInitService.init();
        manufacturerInitService.init();
        networkTechInitService.init();
        em.flush();
        em.clear();

        // when
        service.init();
        em.flush();
        em.clear();

        // then
        List<PhoneModel> phoneModels = repository.findAll();
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getSize).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getSizeDescription())
                .toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getWeight).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getBatteryCapacity).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getMemoryCapacity).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getScreenSize).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(phoneModel -> phoneModel.getDescription().getMemoryDescription()).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getReleaseDate).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getConvenienceFunctions).toList())
                .anyMatch(functions -> functions.size() > 0);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getThumbnail).toList()).allMatch(Objects::nonNull);
        Assertions.assertThat(phoneModels.stream().map(PhoneModel::getAllProducts).toList()).allMatch(products -> products.size() > 0);
    }

    private Map<String, PhoneModel> createPhoneModelMap() {
        String resource1 = "datas/5g-products.txt";
        String resource2 = "datas/4g-products.txt";
        Map<String, PhoneModel> phoneModels = service.convertJsonToPhoneModels(resource1, "5G");
        phoneModels.putAll(service.convertJsonToPhoneModels(resource2, "LTE"));
        service.deletePhonesForInit(phoneModels);
        return phoneModels;
    }

}