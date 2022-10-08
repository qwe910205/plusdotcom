package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ColorCode {

    private String code;

    public ColorCode(String code) {
        if (!Pattern.matches("^#[0-9a-fA-F]{6}$", code))
            throw new IllegalArgumentException("알맞은 색상 코드의 형식을 입력바랍니다.");
        this.code = code;
    }
}
