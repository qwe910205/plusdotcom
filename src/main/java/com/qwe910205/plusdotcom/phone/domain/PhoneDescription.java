package com.qwe910205.plusdotcom.phone.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PhoneDescription {

    private String cpuDescription;
    private String displayDescription;
    private String sizeDescription;
    private String cameraDescription;
    private String memoryDescription;
    private String batteryDescription;
    private String waterproofDescription;

    @Builder
    private PhoneDescription(String cpuDescription, String displayDescription, String sizeDescription, String cameraDescription, String memoryDescription, String batteryDescription, String waterproofDescription) {
        this.cpuDescription = cpuDescription;
        this.displayDescription = displayDescription;
        this.sizeDescription = sizeDescription;
        this.cameraDescription = cameraDescription;
        this.memoryDescription = memoryDescription;
        this.batteryDescription = batteryDescription;
        this.waterproofDescription = waterproofDescription;
    }

    public PhoneDescription changedDescription(String cpuDescription, String displayDescription, String sizeDescription, String cameraDescription, String memoryDescription, String batteryDescription, String waterproofDescription) {
        if (Objects.isNull(cpuDescription))
            cpuDescription = this.cpuDescription;
        if (Objects.isNull(displayDescription))
            displayDescription = this.displayDescription;
        if (Objects.isNull(sizeDescription))
            sizeDescription = this.sizeDescription;
        if (Objects.isNull(cameraDescription))
            cameraDescription = this.cameraDescription;
        if (Objects.isNull(memoryDescription))
            memoryDescription = this.memoryDescription;
        if (Objects.isNull(batteryDescription))
            batteryDescription = this.batteryDescription;
        if (Objects.isNull(waterproofDescription))
            waterproofDescription = this.waterproofDescription;
        return new PhoneDescription(cpuDescription, displayDescription, sizeDescription, cameraDescription, memoryDescription, batteryDescription, waterproofDescription);
    }
}
