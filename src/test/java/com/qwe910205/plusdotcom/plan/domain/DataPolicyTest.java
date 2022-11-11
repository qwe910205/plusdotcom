package com.qwe910205.plusdotcom.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

class DataPolicyTest {

    @Test
    @DisplayName("데이터 정책에 기본 데이터 제공량이 존재하지 않으면 무제한 데이터 정책이다.")
    void isUnlimited() {
        Plan plan = createPlan();
        DataPolicy dataPolicy = new DataPolicy(plan, null);

        boolean result = dataPolicy.isUnlimited();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("데이터 정책이 무제한 데이터 정책이라면 세부사항을 추가하면 예외가 발생한다.")
    void addDataPolicyDetailThatHasNotAdditionalCharge_1() {
        Plan plan = createPlan();
        DataPolicy dataPolicy = new DataPolicy(plan, null);

        assertThatThrownBy(() -> dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(0, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("데이터 정책에 데이터 경곗값이 0인 세부사항이 추가되지 않았을 때 데이터 경곗값이 0이 아닌 세부사항을 추가하면 예외가 발생한다.")
    void addDataPolicyDetailThatHasNotAdditionalCharge_2() {
        Plan plan = createPlan();
        DataPolicy dataPolicy = new DataPolicy(plan, 1000);
        int dataBoundary = 10;
        assertThatThrownBy(() -> dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(dataBoundary, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("데이터 정책에 데이터 경곗값이 0인 세부사항이 추가되었다면 데이터 경곗값이 0이 아닌 세부사항을 추가할 수 있다.")
    void addDataPolicyDetailThatHasNotAdditionalCharge_3() {
        Plan plan = createPlan();
        DataPolicy dataPolicy = new DataPolicy(plan, 1000);
        dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(0, null);

        assertThatCode(() -> dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(10, null))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("데이터 정책의 마지막 세부사항 이외의 세부사항이 속도 제한을 가지면 예외가 발생한다.")
    void addDataPolicyDetailThatHasNotAdditionalCharge_4() {
        Plan plan = createPlan();
        DataPolicy dataPolicy = new DataPolicy(plan, 1000);
        dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(0, 10L);

        assertThatThrownBy(() -> dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(10, 20L))
                .isInstanceOf(IllegalArgumentException.class);
    }

//    @Test
//    @DisplayName("데이터 정책의 데이터 정책 세부사항들의 데이터 경곗값의 첫 번째 이외의 값들을 리스트로 만들 수 있다.")
//    void getDataBoundariesExceptFirst_1() {
//        Plan plan = createPlan();
//        DataPolicy dataPolicy = new DataPolicy(plan, 1000);
//        int[] dataBoundaries = {0, 10, 30, 20};
//        Arrays.stream(dataBoundaries)
//                .forEach(dataBoundary -> dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(dataBoundary, null));
//
//        List<Integer> dataBoundariesExceptFirst = dataPolicy.getDataBoundariesExceptFirst();
//
//        assertThat(dataBoundariesExceptFirst).containsOnly(10, 30, 20);
//    }

//    @Test
//    @DisplayName("데이터 정책이 세부사항을 하나도 가지고 있지 않다면 데이터 경곗값의 첫 번째 이외의 값들을 리스트로 만들려고 하면 빈 리스트가 만들어진다.")
//    void getDataBoundariesExceptFirst_2() {
//        Plan plan = createPlan();
//        DataPolicy dataPolicy = new DataPolicy(plan, 1000);
//
//        List<Integer> dataBoundaries = dataPolicy.getDataBoundariesExceptFirst();
//
//        assertThat(dataBoundaries.size()).isZero();
//    }

//    @Test
//    @DisplayName("데이터 정책 세부사항과 데이터 사용량을 이용해 부과 요금을 주는 객체 리스트를 만들 수 있다.")
//    void getObjectsThatGivesCharge_1() {
//        Plan plan = createPlan();
//        DataPolicy dataPolicy = new DataPolicy(plan, 0);
//        int[] dataBoundaries = {0, 10, 20, 30, 40, 50};
//        long remainDataUsage = 35;
//        Arrays.stream(dataBoundaries).forEach(dataBoundary -> dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(dataBoundary, null));
//
//        List<DataPolicy.ObjectThatGivesCharge> objectsThatGivesCharge = dataPolicy.getObjectsThatGivesCharge(remainDataUsage);
//
//        assertThat(objectsThatGivesCharge.get(0).checkData()).isEqualTo(10);
//        assertThat(objectsThatGivesCharge.get(1).checkData()).isEqualTo(10);
//        assertThat(objectsThatGivesCharge.get(2).checkData()).isEqualTo(10);
//        assertThat(objectsThatGivesCharge.get(3).checkData()).isEqualTo(5);
//        assertThat(objectsThatGivesCharge.get(4).checkData()).isNegative();
//        assertThat(objectsThatGivesCharge.get(5).checkData()).isNegative();
//    }

//    @Test
//    @DisplayName("데이터 정책 세부사항이 하나도 없을 때 데이터 사용량을 이용해 부과 요금을 주는 객체 리스트를 만들면 리스트의 항목이 하나도 없다.")
//    void getObjectsThatGivesCharge_2() {
//        Plan plan = createPlan();
//        DataPolicy dataPolicy = new DataPolicy(plan, 10);
//
//        List<DataPolicy.ObjectThatGivesCharge> objectsThatGivesCharge = dataPolicy.getObjectsThatGivesCharge(100);
//
//        assertThat(objectsThatGivesCharge.size()).isZero();
//    }

    @Test
    @DisplayName("데이터 정책이 무제한 데이터 정책이라면 데이터 사용에 따른 추가적인 비용은 0이다")
    void getAdditionalChargeAbout_1() {
        DataPolicy dataPolicy = new DataPolicy(createPlan(), null);

        long additionalCharge = dataPolicy.getAdditionalChargeAbout(Long.MAX_VALUE);

        assertThat(additionalCharge).isZero();
    }

    @Test
    @DisplayName("데이터 사용량이 데이터 정책의 기본 데이터 제공량을 초과하지 않는다면 부과 비용은 0이다.")
    void getAdditionalChargeAbout_2() {
        Plan plan = createPlan();
        DataPolicy dataPolicy = new DataPolicy(plan, 5000);
        long dataUsage = 4999;

        long charge = dataPolicy.getAdditionalChargeAbout(dataUsage);

        assertThat(charge).isZero();
    }

    @Test
    @DisplayName("데이터 정책은 데이터 사용량에 따른 추가적인 비용을 계산할 수 있다.")
    void getAdditionalChargeAbout_3() {
        Plan plan = createPlan();
        DataPolicy dataPolicy = new DataPolicy(plan, 10);
        int[] dataBoundaries = {0, 10, 20, 30, 40, 50};
        long dataUsage = 35;
        Arrays.stream(dataBoundaries).forEach(dataBoundary -> dataPolicy.addDataPolicyDetail(dataBoundary, null, 1000, 1, null));

        long additionalChargeAbout = dataPolicy.getAdditionalChargeAbout(dataUsage);

        assertThat(additionalChargeAbout).isEqualTo(25);
    }

    @Test
    @DisplayName("데이터 정책의 최대 데이터 사용량은 마지막 정책 세부사항이 데이터를 사용할 수 없다면 기본 데이터 제공량에 마지막 정책 세부사항의 데이터 경곗값을 더한 값이다.")
    void getMaxDataUsage_1() {
        DataPolicy dataPolicy = new DataPolicy(createPlan(), 2000);
        dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(0, null);
        dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(3000, 0L);

        double maxDataUsage = dataPolicy.getMaxDataUsage();

        assertThat(maxDataUsage).isEqualTo(5000);
    }

    @Test
    @DisplayName("데이터 정책의 최대 데이터 사용량은 마지막 정책 세부사항이 데이터를 사용가능하면 무한대이다.")
    void getMaxDataUsage_2() {
        DataPolicy dataPolicy = new DataPolicy(createPlan(), 2000);
        dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(0, null);
        dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(3000, 500L);

        double maxDataUsage = dataPolicy.getMaxDataUsage();

        assertThat(maxDataUsage).isInfinite();
    }

    @Nested
    @DisplayName("availableAmountOfDataWithoutSpeedLimitWhenPayFor 메소드는")
    class Describe_availableAmountOfDataWithoutSpeedLimitWhenPayFor {
        @Nested
        @DisplayName("데이터 정책이 무제한 데이터 정책일 때 지불 금액이 주어지면")
        class Context_given_payment_when_data_policy_is_unlimited {
            private long payment = 1;
            @Test
            @DisplayName("무한대를 반환한다.")
            void it_returns_positive_infinite() {
                DataPolicy dataPolicy = new DataPolicy(createPlan(), null);

                double result = dataPolicy.availableAmountOfDataWithoutSpeedLimitWhenPayFor(payment);

                assertThat(result).isInfinite();
            }
        }
        @Nested
        @DisplayName("지불 금액이 주어지면")
        class Context_with_payment {
            private long payment = 1500;
            @Test
            @DisplayName("사용할 수 있는 속도 제한 없는 데이터양을 반환한다.")
            void it_returns_available_additional_amount_of_data_without_speed_limit() {
                DataPolicy dataPolicy = new DataPolicy(createPlan(), 2000);
                dataPolicy.addDataPolicyDetailThatHasNotAdditionalCharge(0, null);
                dataPolicy.addDataPolicyDetail(1000, null, 500, 1000, null);
                dataPolicy.addDataPolicyDetail(2000, 500L, 1, 50, null);

                double amountOfData = dataPolicy.availableAmountOfDataWithoutSpeedLimitWhenPayFor(payment);

                assertThat(amountOfData).isEqualTo(3500);
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