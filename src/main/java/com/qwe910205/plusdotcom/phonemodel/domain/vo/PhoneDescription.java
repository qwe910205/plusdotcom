package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    public PhoneDescription(String cpuDescription, String displayDescription, String sizeDescription, String cameraDescription, String memoryDescription, String batteryDescription, String waterproofDescription) {
        this.cpuDescription = cpuDescription;
        this.displayDescription = displayDescription;
        this.sizeDescription = sizeDescription;
        this.cameraDescription = cameraDescription;
        this.memoryDescription = memoryDescription;
        this.batteryDescription = batteryDescription;
        this.waterproofDescription = waterproofDescription;
    }

    public PhoneDescription changedDescription(String cpuDescription, String displayDescription, String sizeDescription, String cameraDescription, String memoryDescription, String batteryDescription, String waterproofDescription) {
        if (cpuDescription == null)
            cpuDescription = this.cpuDescription;
        if (displayDescription == null)
            displayDescription = this.displayDescription;
        if (sizeDescription == null)
            sizeDescription = this.sizeDescription;
        if (cameraDescription == null)
            cameraDescription = this.cameraDescription;
        if (memoryDescription == null)
            memoryDescription = this.memoryDescription;
        if (batteryDescription == null)
            batteryDescription = this.batteryDescription;
        if (waterproofDescription == null)
            waterproofDescription = this.waterproofDescription;
        return new PhoneDescription(cpuDescription, displayDescription, sizeDescription, cameraDescription, memoryDescription, batteryDescription, waterproofDescription);
    }
}
