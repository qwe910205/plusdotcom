package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanCategoryName;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(of = {"name"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PlanCategory {

    @AttributeOverride(name = "value", column = @Column(name = "NAME"))
    @EmbeddedId
    private PlanCategoryName name;

    public PlanCategory(String name) {
        this.name = new PlanCategoryName(name);
    }

    public String getName() {
        return name.getValue();
    }
}
