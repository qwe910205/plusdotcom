package com.qwe910205.plusdotcom.phone.domain.wrapper;

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

    private String value;

    public ColorCode(String value) {
        checkIntegrity(value);
        this.value = value;
    }

    private void checkIntegrity(String value) {
        if (!Pattern.matches("^#[0-9a-fA-F]{1,6}$", value))
            throw new IllegalArgumentException(value + "는 알맞지 않는 색상 코드입니다.");
    }
}
