package com.qwe910205.plusdotcom.payment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class InstallmentFeeCalculatorTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("할부 기간과 할부원금으로 할부수수료를 계산할 수 있다.")
    void calculateInstallmentFee(int installmentPeriod, int installmentPrinciple, int installmentFee) {
        int calculatedFee = InstallmentFeeCalculator.calculate(installmentPeriod, installmentPrinciple);

        assertThat(calculatedFee).isEqualTo(installmentFee);
    }

    private static Stream<Arguments> calculateInstallmentFee() {
        return Stream.of(
                Arguments.of(24, 1429700, 89519),
                Arguments.of(12, 1429700, 46102),
                Arguments.of(24, 297000, 18596),
                Arguments.of(0, 1000000, 0),
                Arguments.of(11, 1000000, 0)
        );
    }

}