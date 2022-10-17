package com.qwe910205.plusdotcom.plan.domain.wrapper;

import com.qwe910205.plusdotcom.plan.domain.DataExcessChargePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class DataExcessChargePolicyTest {

    @Test
    @DisplayName("최대 비용이 무제한인 데이터 초과 사용 정책을 생성할 수 있다.")
    void createDataExcessChargePolicyThatHasUnlimitedMaxCost() {
        int dataUnit = 1;
        double cost = 1;

        assertThatCode(() -> new DataExcessChargePolicy(dataUnit, cost)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("데이터 초과 사용 정책의 데이터 단위가 0이나 음수이거나 단위 당 비용이 음수이거나 최대 비용이 음수이면 예외가 발생한다.")
    void createDataExcessChargePolicyWithInvalidValue(int dateUnit, double cost, long maxCost) {
        assertThatThrownBy(() -> new DataExcessChargePolicy(dateUnit, cost, maxCost)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> createDataExcessChargePolicyWithInvalidValue() {
        return Stream.of(
            Arguments.of(0, Double.MAX_VALUE, Long.MAX_VALUE),
            Arguments.of(-1, Double.MAX_VALUE, Long.MAX_VALUE),
            Arguments.of(Integer.MAX_VALUE, -1, Long.MAX_VALUE),
            Arguments.of(Integer.MAX_VALUE, Double.MAX_VALUE, -1)
        );
    }
}