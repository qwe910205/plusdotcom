package com.qwe910205.plusdotcom.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class PlanTest {

    @Test
    @DisplayName("이미 추가된 프리미엄 서비스와 이름이 같은 프리미엄 서비스를 추가하면 대체된다.")
    void addPremiumService() {
        String serviceName = "디즈니+";
        String image = "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/plan-sp-disneyplus.png";
        Plan plan = createPlan();
        plan.addPremiumService(new PremiumService(serviceName, image));
        String newImage = "https://naver.com";
        PremiumService duplicatedService = new PremiumService(serviceName, newImage);

        plan.addPremiumService(duplicatedService);

        assertThat(plan.getPremiumServices().size()).isEqualTo(1);
        List<PremiumService> premiumServices = plan.getPremiumServices();
        assertThat(premiumServices.get(0).getImage()).isEqualTo(newImage);
    }

    @Test
    @DisplayName("등록되지 않은 단위 기간의 데이터 정책을 조회하면 예외가 발생한다.")
    void getDataPolicy() {
        Plan plan = createPlan();
        plan.putUnLimitedDataPolicy(DataPolicyUnitPeriod.MONTH);

        assertThatThrownBy(() -> plan.getDataPolicy(DataPolicyUnitPeriod.DAY))
                .hasMessage(DataPolicyUnitPeriod.DAY + " 단위의 데이터 정책은 등록되지 않았습니다.")
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("이미 추가된 데이터 정책 단위 기간에 대한 데이터 정책을 다른 데이터 정책으로 추가하면 대체된다.")
    void putLimitedDataPolicy_1() {
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
    void putLimitedDataPolicy_2() {
        Plan plan = createPlan();
        plan.putUnLimitedDataPolicy(DataPolicyUnitPeriod.MONTH);

        assertThatThrownBy(() -> plan.putLimitedDataPolicy(DataPolicyUnitPeriod.DAY, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("요금제가 월간 데이터 정책 이외의 데이터 정책을 가지고 있을 때 아래 함수를 사용하면 거짓을 반환한다.")
    void canCalculateThingsRelatedToMonth() {
        Plan plan = createPlan();
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.DAY, 5000);
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 5500);

        boolean result = plan.canCalculateThingsRelatedToMonth();

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("요금제가 월간 데이터 정책 이외의 데이터 정책을 가지고 있을 때는 월간 데이터 사용량에 대한 비용을 계산할 수 없다.")
    void getChargeAboutMonthlyDataUsage_1() {
        Plan plan = createPlan();
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.DAY, 5000);
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 5500);

        assertThatThrownBy(() -> plan.getChargeAboutMonthlyDataUsage(10))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("월간 데이터 사용량이 월간 최대 데이터 사용량을 초과한다면 월간 데이터 사용량에 대한 비용을 계산할 수 없다.")
    void getChargeAboutMonthlyDataUsage_2() {
        Plan plan = createPlan();
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 10);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 0, null, 1000, 1, null);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 10, null, 1000, 1, null);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 20, null, 1000, 2, null);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 30, null, 1000, 1, null);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 40, 0L, 1000, 1, null);
        long dataUsage = 51;

        assertThatThrownBy(() -> plan.getChargeAboutMonthlyDataUsage(dataUsage)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("월간 데이터 사용량에 대한 비용을 계산할 수 있다.")
    void getChargeAboutMonthlyDataUsage_3() {
        Plan plan = createPlan();
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 10);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 0, null, 1000, 1, null);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 10, null, 1000, 1, null);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 20, null, 1000, 2, null);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 30, null, 1000, 1, null);
        plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 40, null, 1000, 1, null);
        long dataUsage = 35;

        long charge = plan.getChargeAboutMonthlyDataUsage(dataUsage);

        assertThat(charge).isEqualTo(plan.getBasicMonthlyCharge() + 30);
    }

    @Test
    @DisplayName("요금제가 월간 데이터 정책 이외의 데이터 정책을 가지고 있다면 한 달간 최대 데이터 사용량을 구할 수 없다.")
    void availableMonthlyAmountOfData_1() {
        Plan plan = createPlan();
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 2000);
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.DAY, 2000);

        assertThatThrownBy(plan::availableMonthlyAmountOfData).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("요금제의 한 달간 최대 데이터 사용량을 구할 수 있다.")
    void availableMonthlyAmountOfData_2() {
        Plan plan = createPlan();
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 2000);
        plan.addDataPolicyDetailThatHasNotAdditionalCharge(DataPolicyUnitPeriod.MONTH, 0, null);
        plan.addDataPolicyDetailThatHasNotAdditionalCharge(DataPolicyUnitPeriod.MONTH, 2000, null);
        plan.addDataPolicyDetailThatHasNotAdditionalCharge(DataPolicyUnitPeriod.MONTH, 4000, 0L);

        double maxMonthlyDataUsage = plan.availableMonthlyAmountOfData();

        assertThat(maxMonthlyDataUsage).isEqualTo(6000);
    }

    @Test
    @DisplayName("요금제의 한 달간 최대 데이터 사용량이 무한대일 경우에도 구할 수 있다.")
    void availableMonthlyAmountOfData_3() {
        Plan plan = createPlan();
        plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 2000);
        plan.addDataPolicyDetailThatHasNotAdditionalCharge(DataPolicyUnitPeriod.MONTH, 0, null);
        plan.addDataPolicyDetailThatHasNotAdditionalCharge(DataPolicyUnitPeriod.MONTH, 2000, null);

        double maxMonthlyDataUsage = plan.availableMonthlyAmountOfData();

        assertThat(maxMonthlyDataUsage).isInfinite();
    }

    @Nested
    @DisplayName("availableMonthlyAmountOfDataWithoutSpeedLimitWhenPayFor 메소드는")
    class Describe_availableMonthlyAmountOfDataWithoutSpeedLimitAt {
        @Nested
        @DisplayName("기본 월정액보다 적은 지불 금액이 주어지면")
        class Context_with_cost_less_than_basic_monthly_charge {
            private final int payment = 5000;
            @Test
            @DisplayName("0을 반환한다.")
            void it_returns_zero() {
                Plan plan = createPlan();
                plan.putUnLimitedDataPolicy(DataPolicyUnitPeriod.MONTH);
                double amountOfData = plan.availableMonthlyAmountOfDataWithoutSpeedLimitWhenPayFor(payment);

                assertThat(amountOfData).isZero();
            }
        }
        @Nested
        @DisplayName("기본 월정액보다 크거가 같은 지불 금액이 주어졌을 때 무제한 요금제라면")
        class Context_with_cost_greater_than_or_equal_basic_monthly_charge_and_unlimited_plan {
            private final int payment = 105000;
            @Test
            @DisplayName("무한대를 반환한다.")
            void it_returns_infinite() {
                Plan plan = createPlan();
                plan.putUnLimitedDataPolicy(DataPolicyUnitPeriod.MONTH);
                double amountOfData = plan.availableMonthlyAmountOfDataWithoutSpeedLimitWhenPayFor(payment);

                assertThat(amountOfData).isInfinite();
            }
        }
        @Nested
        @DisplayName("기본 월정액보다 크거가 같은 지불 금액이 주어지면")
        class Context_with_cost_greater_than_or_equal_basic_monthly_charge {
            private final int payment = 150000;
            @Test
            @DisplayName("한 달간 사용할 수 있는 무제한 속도의 데이터양을 반환한다.")
            void it_returns_available_amount_of_unlimited_data() {
                Plan plan = createPlan();
                plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 2000);
                plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 0, null, 1, 10, null);
                double amountOfData = plan.availableMonthlyAmountOfDataWithoutSpeedLimitWhenPayFor(payment);

                assertThat(amountOfData).isEqualTo(6500);
            }
        }
    }

    @Nested
    @DisplayName("availableMonthlyAmountOfDataWhenPayFor 메소드는")
    class Describe_availableMonthlyAmountOfDataWhenPayFor {
        @Nested
        @DisplayName("지불 금액이 주어지면")
        class Context_given_payment {
            private final long payment = 150000;
            @Test
            @DisplayName("한 달간 사용할 수 있는 데이터양을 반환한다.")
            void it_returns_available_amount_of_data() {
                Plan plan = createPlan();
                plan.putLimitedDataPolicy(DataPolicyUnitPeriod.MONTH, 2000);
                plan.addDataPolicyDetail(DataPolicyUnitPeriod.MONTH, 0, null, 1, 10, null);
                double amountOfData = plan.availableMonthlyAmountOfDataWhenPayFor(payment);

                assertThat(amountOfData).isEqualTo(6500);
            }
        }
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
}