package com.qwe910205.plusdotcom.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class DataPolicyDetailTest {

    @Test
    @DisplayName("초과 데이터 사용량에 대한 비용 정책이 없는 데이터 정책 세부사항이라면 데이터 사용량에 대한 비용은 무조건 0이다.")
    void getChargeAbout_1() {
        Plan plan = createPlan();
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(plan, 1000), 0, null);

        long charge = dataPolicyDetail.getChargeAbout(10000);

        assertThat(charge).isZero();
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    @DisplayName("데이터 사용량이 0보다 작거나 같으면 데이터 사용량에 대한 비용은 무조건 0이다.")
    void getChargeAbout_2(long dataUsage) {
        Plan plan = createPlan();
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(plan, 1000), 0, null);

        long charge = dataPolicyDetail.getChargeAbout(dataUsage);

        assertThat(charge).isZero();
    }

    @Test
    @DisplayName("데이터 정책 세부사항이 만약 속도 제한이 0이라면 데이터를 사용할 수 없다.")
    void availableData_1() {
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(createPlan(), 0), 0, 0L);

        boolean available = dataPolicyDetail.canUseData();

        assertThat(available).isFalse();
    }

    @Test
    @DisplayName("데이터 정책 세부사항이 만약 속도 제한이 없다면 데이터를 사용할 수 있다.")
    void availableData_2() {
        DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(createPlan(), 0), 0, null);

        boolean available = dataPolicyDetail.canUseData();

        assertThat(available).isTrue();
    }

    @Nested
    @DisplayName("availableAmountOfDataWithoutSpeedLimitWhenPayFor 메소드는")
    class Describe_availableAmountOfDataWithoutSpeedLimitWhenPayFor {
        @Nested
        @DisplayName("만약 속도 제한이 있는 정책 세부사항에 지불 금액이 주어지면")
        class Context_with_payment_when_detail_has_speed_limit {
            private final long payment = Long.MAX_VALUE;
            @Test
            @DisplayName("0을 반환한다.")
            void it_returns_zero() {
                DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(createPlan(), 5000), 0, 1L);
                dataPolicyDetail.setChargePolicyAboutExcessDataUsage(new ChargePolicyAboutExcessDataUsage(1, 1));

                double result = dataPolicyDetail.availableAmountOfDataWithoutSpeedLimitWhenPayFor(payment);

                assertThat(result).isZero();
            }
        }
        @Nested
        @DisplayName("만약 초과 데이터 사용에 대한 부과 비용 정책이 존재하지 않는다면 지불 금액이 얼마든")
        class Context_not_exists_charge_policy_about_excess_data_usage {
            private final long payment = 1;
            @Test
            @DisplayName("무한대를 반환한다.")
            void it_returns_positive_infinite() {
                DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(createPlan(), 5000), 0, null);

                double result = dataPolicyDetail.availableAmountOfDataWithoutSpeedLimitWhenPayFor(payment);

                assertThat(result).isInfinite();
            }
        }

        @Nested
        @DisplayName("지불 금액이 주어지면")
        class Context_with_payment {
            private final long payment = 50000;
            @Test
            @DisplayName("그에 맞는 속도 제한 없이 사용할 수 있는 데이터양을 반환한다.")
            void it_returns_available_amount_of_data_without_speed_limit() {
                DataPolicyDetail dataPolicyDetail = new DataPolicyDetail(new DataPolicy(createPlan(), 5000), 0, null);
                dataPolicyDetail.setChargePolicyAboutExcessDataUsage(new ChargePolicyAboutExcessDataUsage(1, 1));

                double result = dataPolicyDetail.availableAmountOfDataWithoutSpeedLimitWhenPayFor(payment);

                assertThat(result).isEqualTo(50000);
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