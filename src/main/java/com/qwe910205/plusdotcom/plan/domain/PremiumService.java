package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.ImageSource;
import com.qwe910205.plusdotcom.plan.domain.wrapper.ServiceName;
import lombok.EqualsAndHashCode;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@EqualsAndHashCode(of = {"name"})
@Entity
public class PremiumService {

    @EmbeddedId
    private ServiceName name;

    private ImageSource image;
}
