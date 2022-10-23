package com.qwe910205.plusdotcom.phone.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.Price;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@EqualsAndHashCode(of = {"phoneModel", "plan"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(
        name = "PHONE_MODEL_PLAN_UNIQUE",
        columnNames = {"PHONE_MODEL_ID", "PLAN_ID"}
))
@Entity
public class PubliclySubsidy {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private PhoneModel phoneModel;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Plan plan;

    @AttributeOverride(name = "value", column = @Column(name = "amount", nullable = false))
    @Embedded
    private Price amount;

    PubliclySubsidy(PhoneModel phoneModel, Plan plan, int amount) {
        checkIntegrity(phoneModel, plan, amount);
        this.phoneModel = phoneModel;
        this.plan = plan;
        this.amount = new Price(amount);
    }

    private void checkIntegrity(PhoneModel phoneModel, Plan plan, int amount) {
        Objects.requireNonNull(phoneModel, "공시지원금을 설정하려면 스마트폰 모델이 필요합니다.");
        Objects.requireNonNull(plan, "공시지원금을 설정하려면 요금제가 필요합니다.");
        if (amount < 0)
            throw new IllegalArgumentException("공시지원금은 음수일 수 없습니다.");
    }

    public int getAmount() {
        return this.amount.getValue();
    }
}
