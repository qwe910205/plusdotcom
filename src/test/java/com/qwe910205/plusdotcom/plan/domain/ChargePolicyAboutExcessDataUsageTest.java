package com.qwe910205.plusdotcom.plan.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ChargePolicyAboutExcessDataUsageTest {

    @Test
    @DisplayName("최대 비용이 무제한인 데이터 초과 사용 정책을 생성할 수 있다.")
    void createChargePolicyThatHasUnlimitedMaxCost() {
        int dataUnit = 1;
        double cost = 1;

        assertThatCode(() -> new ChargePolicyAboutExcessDataUsage(dataUnit, cost)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("데이터 초과 사용 정책의 데이터 단위가 0이나 음수이거나 단위 당 비용이 음수이거나 최대 비용이 음수이면 예외가 발생한다.")
    void createChargePolicyWithInvalidValue_1(int dateUnit, double cost, int maxCost) {
        assertThatThrownBy(() -> new ChargePolicyAboutExcessDataUsage(dateUnit, cost, maxCost)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> createChargePolicyWithInvalidValue_1() {
        return Stream.of(
            Arguments.of(0, Double.MAX_VALUE, Integer.MAX_VALUE),
            Arguments.of(-1, Double.MAX_VALUE, Integer.MAX_VALUE),
            Arguments.of(Integer.MAX_VALUE, -1, Integer.MAX_VALUE),
            Arguments.of(Integer.MAX_VALUE, Double.MAX_VALUE, -1)
        );
    }

    @Test
    @DisplayName("단위 데이터에 비해 비용이 너무 크면 예외가 발생한다.")
    void createChargePolicyWithInvalidValue_2() {
        int dataUnit = 1000;
        double cost = Double.MAX_VALUE;

        assertThatThrownBy(() -> new ChargePolicyAboutExcessDataUsage(dataUnit, cost))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("최대 부과 비용이 정해지지 않았다면 아래 함수를 실행하면 거짓이 반환된다.")
    void hasMaximumCharge() {
        ChargePolicyAboutExcessDataUsage chargePolicyAboutExcessDataUsage = new ChargePolicyAboutExcessDataUsage(5000, 22.53);

        boolean result = chargePolicyAboutExcessDataUsage.hasMaximumCharge();

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("데이터 사용량에 대한 부과 비용은 데이터 사용량에 단위 데이터를 나눈 것에 비용을 곱한 것이다.")
    void getChargeAbout() {
        int dataUnit = 1;
        double cost = 1000;
        ChargePolicyAboutExcessDataUsage chargePolicyAboutExcessDataUsage = new ChargePolicyAboutExcessDataUsage(dataUnit, cost);
        long dataUsage = 10000000;

        long charge = chargePolicyAboutExcessDataUsage.getChargeAbout(dataUsage);

        long assertValue = new BigDecimal(dataUsage)
                        .multiply(new BigDecimal(1000)
                        .divide(new BigDecimal(dataUnit), RoundingMode.UP)
                        .multiply(new BigDecimal(cost)))
                        .longValue();
        assertThat(charge).isEqualTo(assertValue);
    }

    @Test
    @DisplayName("데이터 사용량이 10TB를 초과하면 예외가 발생한다.")
    void getChargeAboutTooLargeDataUsage() {
        int dataUnit = 1;
        double cost = 1000;
        ChargePolicyAboutExcessDataUsage chargePolicyAboutExcessDataUsage = new ChargePolicyAboutExcessDataUsage(dataUnit, cost);
        long dataUsage = 10000001;

        assertThatThrownBy(() -> chargePolicyAboutExcessDataUsage.getChargeAbout(dataUsage)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("데이터 사용량이 단위 데이터보다 적어도 비용이 부과된다.")
    void getChargeAboutMinimumDataUsage() {
        int dataUnit = 5000000;
        double cost = 100000.0;
        ChargePolicyAboutExcessDataUsage chargePolicyAboutExcessDataUsage = new ChargePolicyAboutExcessDataUsage(dataUnit, cost);
        int dataUsage = 1;

        long charge = chargePolicyAboutExcessDataUsage.getChargeAbout(dataUsage);

        assertThat(charge).isEqualTo((int) cost);
    }

    @Test
    @DisplayName("부과 비용은 정해진 최대 부과 비용을 초과하지 않는다.")
    void getChargeAboutDataUsageThatExcessMaximumCharge() {
        int dataUnit = 1;
        double cost = 1000;
        int maximumCharge = 1;
        ChargePolicyAboutExcessDataUsage chargePolicyAboutExcessDataUsage = new ChargePolicyAboutExcessDataUsage(dataUnit, cost, maximumCharge);
        long dataUsage = 10000000;

        long charge = chargePolicyAboutExcessDataUsage.getChargeAbout(dataUsage);

        assertThat(charge).isLessThanOrEqualTo(maximumCharge);
    }
}