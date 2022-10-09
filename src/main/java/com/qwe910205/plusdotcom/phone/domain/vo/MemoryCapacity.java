package com.qwe910205.plusdotcom.phone.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemoryCapacity {

    /** 단위는 GB */
    private Integer ram;

    /** 단위는 GB */
    private Integer rom;

    public MemoryCapacity(int ram, int rom) {
        if (ram < 0 || rom < 0)
            throw new IllegalArgumentException("메모리의 저장 용량은 음수일 수 없습니다.");
        this.ram = ram;
        this.rom = rom;
    }
}
