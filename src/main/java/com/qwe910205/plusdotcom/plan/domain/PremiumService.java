package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.ImageSource;
import com.qwe910205.plusdotcom.plan.domain.vo.ServiceName;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class PremiumService {

    @EmbeddedId
    private ServiceName name;

    private ImageSource image;
}
