package com.qwe910205.plusdotcom.plan.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DataExcessChargePolicyTest {

    @Test
    void 데이터_초과_사용_정책을_생성할_수_있다() {
        // given
        int dataUnit = Integer.MAX_VALUE;
        double cost = Double.MAX_VALUE;
        long maxCost = Long.MAX_VALUE;

        // when
        DataExcessChargePolicy dataExcessChargePolicy = new DataExcessChargePolicy(dataUnit, cost, maxCost);

        // then
        assertThat(dataExcessChargePolicy.getDataUnit()).isEqualTo(dataUnit);
        assertThat(dataExcessChargePolicy.getCost()).isEqualTo(cost);
        assertThat(dataExcessChargePolicy.getMaxCost()).isEqualTo(maxCost);
        assertThat(dataExcessChargePolicy.hasCostLimit()).isTrue();
    }

    @Test
    void 데이터_초과_사용_정책의_데이터_단위는_양수여야_한다() {
        // given
        int dataUnit = 0;
        double cost = Double.MAX_VALUE;

        // when
        assertThatThrownBy(() -> new DataExcessChargePolicy(dataUnit, cost, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 데이터_초과_사용_정책의_단위_당_비용은_음수일_수_없다() {
        // given
        int dateUnit = Integer.MAX_VALUE;
        double cost = -1;

        // when
        assertThatThrownBy(() -> new DataExcessChargePolicy(dateUnit, cost, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 데이터_초과_사용_정책의_최대_비용은_음수일_수_없다() {
        // given
        int dataUnit = Integer.MAX_VALUE;
        double cost = Double.MAX_VALUE;
        long maxCost = -1;

        // when then
        assertThatThrownBy(() -> new DataExcessChargePolicy(dataUnit, cost, maxCost)).isInstanceOf(IllegalArgumentException.class);
    }

}