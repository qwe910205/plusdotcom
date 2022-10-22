package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.plan.domain.factory.PlanFactory;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PlanTest {

    @Test
    @DisplayName("중복된 프리미엄 서비스를 요금제가 제공할 수 있는 서비스 목록에 추가하면 추가되지 않는다.")
    void addDuplicatedPremiumService() {
        String serviceName = "디즈니+";
        String image = "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/plan-sp-disneyplus.png";
        PremiumService service = new PremiumService(serviceName, image);
        Plan plan = Plan.builder().id("요금제1").name("요금제1").networkTech("5G").monthlyPayment(50000).category("데이터 일반").build();
        PremiumService duplicatedService = new PremiumService(serviceName, "https://naver.com");

        plan.addPremiumService(duplicatedService);

        assertThat(plan.getPremiumServices().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("등록되지 않은 단위 기간의 데이터 정책을 조회하면 예외가 발생한다.")
    void getUnregisteredDataPolicy() {
        Plan plan = PlanFactory.create("1", "1", "1", 50000, "test");
        plan.addUnLimitDataPolicy(DataPolicyUnitPeriod.MONTH);

        assertThatThrownBy(() -> plan.getDataPolicy(DataPolicyUnitPeriod.DAY))
                .hasMessage(DataPolicyUnitPeriod.DAY + " 단위의 데이터 정책은 등록되지 않았습니다.")
                .isInstanceOf(NoSuchElementException.class);
    }
}