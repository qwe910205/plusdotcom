package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanCategoryName;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(of = {"name"})
@Entity
public class PlanCategory {

    @EmbeddedId
    private PlanCategoryName name;
}
