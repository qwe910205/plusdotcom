package com.qwe910205.plusdotcom.phone.domain.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemoryCapacityTest {

    @Test
    void 메모리의_ram_용량은_음수일_수_없다() {
        // given
        int ram = -1;
        int rom = 1;

        // when
        Assertions.assertThatThrownBy(() -> new MemoryCapacity(ram, rom)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메모리의_rom_용량은_음수일_수_없다() {
        // given
        int ram = 1;
        int rom = -1;

        // when
        Assertions.assertThatThrownBy(() -> new MemoryCapacity(ram, rom)).isInstanceOf(IllegalArgumentException.class);
    }
}