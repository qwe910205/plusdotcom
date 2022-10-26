package com.qwe910205.plusdotcom.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class PlanTest {

    @Test
    @DisplayName("이미 추가된 프리미엄 서비스와 이름이 같은 프리미엄 서비스를 추가하면 대체된다.")
    void addDuplicatedPremiumService() {
        String serviceName = "디즈니+";
        String image = "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/plan-sp-disneyplus.png";
        Plan plan = createPlan();
        plan.addPremiumService(new PremiumService(serviceName, image));
        PremiumService duplicatedService = new PremiumService(serviceName, "https://naver.com");

        plan.addPremiumService(duplicatedService);

        assertThat(plan.getPremiumServices().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("등록되지 않은 단위 기간의 데이터 정책을 조회하면 예외가 발생한다.")
    void getUnregisteredDataPolicy() {
        Plan plan = createPlan();
        plan.putUnLimitedDataPolicy(DataPolicyUnitPeriod.MONTH);

        assertThatThrownBy(() -> plan.getDataPolicy(DataPolicyUnitPeriod.DAY))
                .hasMessage(DataPolicyUnitPeriod.DAY + " 단위의 데이터 정책은 등록되지 않았습니다.")
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("이미 추가된 데이터 정책 단위 기간에 대한 데이터 정책을 다른 데이터 정책으로 추가하면 대체된다.")
    void putDataPolicy() {
        Plan plan = createPlan();
        int beforeValue = 50000;
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, beforeValue);
        int afterValue = 60000;

        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, afterValue);

        DataPolicy dataPolicy = plan.getDataPolicy(DataPolicyUnitPeriod.MONTH);
        int servingDataQuantity = dataPolicy.getServingDataQuantity();
        assertThat(servingDataQuantity).isEqualTo(afterValue);
    }

    @Test
    @DisplayName("무제한 데이터 정책을 가진 요금제에 데이터 정책을 추가하면 예외가 발생한다.")
    void putDataPolicyInPlanWhenPlanHasUnlimitedDataPolicy() {
        Plan plan = createPlan();
        plan.putUnLimitedDataPolicy(DataPolicyUnitPeriod.MONTH);

        assertThatThrownBy(() -> plan.putLimitedDataPolicy(DataPolicyUnitPeriod.DAY, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("요금제가 월간 데이터 정책 이외의 데이터 정책을 가지고 있을 때 아래 함수를 사용하면 참을 반환한다.")
    void hasDataPolicyOtherThanMonthlyDataPolicy() {
        Plan plan = createPlan();
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.DAY, 5000);
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 5500);

        boolean result = plan.hasDataPolicyOtherThanMonthlyDataPolicy();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("요금제가 월간 데이터 정책 이외의 데이터 정책을 가지고 있을 때는 월간 데이터 사용량에 대한 비용을 계산할 수 없다.")
    void getChargeAboutMonthlyDataUsageWhenPlanHasDataPolicyOtherThanMonthlyDataPolicy() {
        Plan plan = createPlan();
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.DAY, 5000);
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 5500);

        assertThatThrownBy(() -> plan.getChargeAboutMonthlyDataUsage(10))
                .isInstanceOf(RuntimeException.class);
    }

    private Plan createPlan() {
        return Plan.builder()
                .id("Z202205252")
                .name("5G 프리미어 플러스")
                .networkTech("5G")
                .basicMonthlyCharge(105000)
                .category("데이터 무제한")
                .build();
    }
}