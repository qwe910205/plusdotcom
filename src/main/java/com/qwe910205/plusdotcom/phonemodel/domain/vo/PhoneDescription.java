package com.qwe910205.plusdotcom.phonemodel.domain.vo;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@EqualsAndHashCode
@Embeddable
public class PhoneDescription {

    private String cpuDescription;
    private String displayDescription;
    private String sizeDescription;
    private String cameraDescription;
    private String memoryDescription;
    private String batteryDescription;
    private String waterproofDescription;
}
