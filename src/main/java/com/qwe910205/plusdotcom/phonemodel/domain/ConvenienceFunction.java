package com.qwe910205.plusdotcom.phonemodel.domain;

import com.qwe910205.plusdotcom.phonemodel.domain.vo.ConvenienceFunctionName;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(of = {"name"})
@Entity
public class ConvenienceFunction {

    @EmbeddedId
    private ConvenienceFunctionName name;
}
