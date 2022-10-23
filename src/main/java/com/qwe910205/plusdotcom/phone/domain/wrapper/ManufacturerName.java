package com.qwe910205.plusdotcom.phone.domain.wrapper;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ManufacturerName implements Serializable {

    private String value;

    public ManufacturerName(String value) {
        checkIntegrity(value);
        this.value = value;
    }

    private void checkIntegrity(String value) {
        if (!StringUtils.hasText(value))
            throw new IllegalArgumentException("제조사의 이름은 한 글자 이상이어야 합니다.");
    }
}
