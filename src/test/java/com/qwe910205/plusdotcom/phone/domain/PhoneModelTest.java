package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.factory.PhoneModelFactory;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PhoneModelTest {

    @Test
    @DisplayName("같은 해시태그를 스마트폰 모델에 추가하면 아무 변화가 없다.")
    void addDuplicateHashtag() {
        PhoneModel phoneModel = createPhoneModel();
        List<String> hashtags = Arrays.asList("커버디스플레이", "플렉스캠", "대용량배터리");
        phoneModel.addHashTags(hashtags);
        List<String> beforeAdding = phoneModel.getHashtags();

        phoneModel.addHashTags(hashtags);

        List<String> afterAdding = phoneModel.getHashtags();
        assertThat(afterAdding).containsExactlyElementsOf(beforeAdding);
    }

    @Test
    @DisplayName("같은 편의기능을 스마트폰 모델에 추가하면 아무 변화가 없다.")
    void addDuplicateConvenienceFunction() {
        PhoneModel phoneModel = createPhoneModel();
        List<String> convenienceFunctions = Arrays.asList("삼성페이", "지문인식");
        phoneModel.addConvenienceFunctions(convenienceFunctions);
        List<String> beforeAdding = phoneModel.getConvenienceFunctions();

        phoneModel.addHashTags(convenienceFunctions);

        List<String> afterAdding = phoneModel.getConvenienceFunctions();
        assertThat(afterAdding).containsExactlyElementsOf(beforeAdding);
    }

    @Test
    @DisplayName("이미 추가된 요금제에 대한 공시지원금을 다른 값으로 추가하면 다른 값으로 바뀐다.")
    void putPubliclySubsidy() {
        PhoneModel phoneModel = createPhoneModel();
        Plan plan = createPlan();
        int beforeAmount = 50000;
        phoneModel.putPubliclySubsidy(plan, beforeAmount);
        int afterAmount = 60000;

        phoneModel.putPubliclySubsidy(plan, afterAmount);

        int publiclySubsidy = phoneModel.getPubliclySubsidy(plan);
        assertThat(publiclySubsidy).isEqualTo(afterAmount);
    }

    @Test
    @DisplayName("스마트폰 모델과 다른 통신 기술을 사용하는 요금제는 공시지원금을 추가하면 예외가 발생한다.")
    void putPubliclySubsidyWithPlanThatHasDifferentNetworkTech() {
        PhoneModel phoneModel = createPhoneModel();
        Plan ltePlan = createLTEPlan();

        assertThatThrownBy(() -> phoneModel.putPubliclySubsidy(ltePlan, 50000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private PhoneModel createPhoneModel() {
        return PhoneModel.builder()
                .modelId("SM-F721N-MK")
                .name("갤럭시 Z Flip 4 메종키츠네 에디션")
                .manufacturer("SAMSUNG")
                .networkTech("5G")
                .price(1386000)
                .build();
    }

    private Plan createPlan() {
        return Plan.builder()
                .planId("Z202205252")
                .name("5G 프리미어 플러스")
                .networkTech("5G")
                .basicMonthlyCharge(105000)
                .category("데이터 무제한")
                .build();
    }

    private Plan createLTEPlan() {
        return Plan.builder()
                .planId("LPZ0000679")
                .name("LTE 프리미어 에센셜")
                .networkTech("LTE")
                .basicMonthlyCharge(85000)
                .category("데이터 무제한")
                .build();
    }

}