package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.ImageSource;
import com.qwe910205.plusdotcom.plan.domain.vo.ServiceName;
import lombok.EqualsAndHashCode;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@EqualsAndHashCode(of = {"name"})
@Entity
public class MediaService {

    @EmbeddedId
    private ServiceName name;

    private ImageSource image;

}
