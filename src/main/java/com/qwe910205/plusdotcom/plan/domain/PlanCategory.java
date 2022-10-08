package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.plan.domain.vo.CategoryName;

import javax.persistence.*;

@Entity
public class PlanCategory {

    @EmbeddedId
    private CategoryName name;
}
