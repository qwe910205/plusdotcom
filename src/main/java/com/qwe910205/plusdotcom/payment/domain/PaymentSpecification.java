package com.qwe910205.plusdotcom.payment.domain;

import com.qwe910205.plusdotcom.payment.domain.wrapper.CommitmentPeriod;
import com.qwe910205.plusdotcom.payment.domain.wrapper.InstallmentPeriod;
import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.wrapper.Money;
import com.qwe910205.plusdotcom.plan.domain.Plan;

import java.util.Objects;

public class PaymentSpecification {

    private final PhoneField phoneField;

    private final PlanField planField;

    private final DiscountType discountType;

    public PaymentSpecification(PhoneModel phoneModel, Plan plan, int installmentPeriod, DiscountType discountType) {
        if (Objects.isNull(phoneModel))
            throw new IllegalArgumentException("요금 명세서를 생성할 때 스마트폰 모델은 필수입니다.");
        if (Objects.isNull(plan))
            throw new IllegalArgumentException("요금 명세서를 생성할 때 요금제는 필수입니다.");
        if (Objects.isNull(discountType))
            throw new IllegalArgumentException("요금 명세서를 생성할 때 할인 유형은 필수입니다.");
        if (!phoneModel.getNetworkTech().equals(plan.getNetworkTech()))
            throw new IllegalArgumentException("통신 기술이 다른 스마트폰 모델과 요금제는 함께 구매할 수 없습니다.");
        this.phoneField = new PhoneField(phoneModel, installmentPeriod);
        this.planField = new PlanField(plan, discountType);
        this.discountType = discountType;
    }

    public PhoneField getPhoneField() {
        return phoneField;
    }

    public PlanField getPlanField() {
        return planField;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public String getDiscountTypeName() {
        return discountType.getName();
    }

    public void applyPubliclySubsidy() {
        phoneField.applyPubliclySubsidyAbout(planField.plan);
    }
    public void setCommitmentDiscountAmount(int amount) {
        planField.setCommitmentDiscountAmount(amount);
    }

    public Integer getTotalMonthlyPayment() {
        return phoneField.getMonthlyInstallment() + planField.getMonthlyCharge();
    }

    public class PhoneField {

        private final PhoneModel phoneModel;

        private final Money normalPrice;

        private Money publiclySubsidy;

        private Money additionalSubsidy;

        private final InstallmentPeriod installmentPeriod;

        private Money installmentPrinciple;

        private Money installmentFee;

        private Money monthlyInstallment;

        private PhoneField(PhoneModel phoneModel, int installmentPeriod) {
            this.phoneModel = phoneModel;
            this.normalPrice = new Money(phoneModel.getMoney());
            this.publiclySubsidy = new Money(0);
            this.additionalSubsidy = new Money(0);
            this.installmentPeriod = new InstallmentPeriod(installmentPeriod);
            this.installmentPrinciple = new Money(phoneModel.getMoney());
            this.installmentFee = new Money(InstallmentFeeCalculator.calculate(installmentPeriod, installmentPrinciple.getValue()));
            this.monthlyInstallment = installmentPrinciple.plus(installmentFee).divide(installmentPeriod);
        }

        public String getModelId() {
            return phoneModel.getPhoneModelId();
        }

        public int getNormalPrice() {
            return normalPrice.getValue();
        }

        public int getPubliclySubsidy() {
            return publiclySubsidy.getValue();
        }

        public void applyPubliclySubsidyAbout(Plan plan) {
            publiclySubsidy = new Money(phoneModel.getPubliclySubsidy(plan));
            additionalSubsidy = publiclySubsidy.divide(100).multiply(15);
            installmentPrinciple = normalPrice.minus(publiclySubsidy).minus(additionalSubsidy);
            installmentFee = new Money(InstallmentFeeCalculator.calculate(installmentPeriod.value(), installmentPrinciple.getValue()));
            monthlyInstallment = installmentPrinciple.plus(installmentFee).divide(installmentPeriod.value());
        }

        public int getAdditionalSubsidy() {
            return additionalSubsidy.getValue();
        }

        public int getInstallmentPeriod() {
            return installmentPeriod.value();
        }
        public int getInstallmentPrinciple() {
            return installmentPrinciple.getValue();
        }

        public int getInstallmentFee() {
            return installmentFee.getValue();
        }

        public int getMonthlyInstallment() {
            return monthlyInstallment.getValue();
        }
    }

    public class PlanField {

        private final Plan plan;

        private final Money basicMonthlyCharge;

        private CommitmentPeriod commitmentPeriod;

        private Money commitmentDiscountAmount;

        private Money monthlyCharge;

        private PlanField(Plan plan, DiscountType discountType) {
            this.plan = plan;
            this.basicMonthlyCharge = new Money(plan.getBasicMonthlyCharge());
            this.commitmentPeriod = new CommitmentPeriod(0);
            if (discountType == DiscountType.COMMITMENT_12MONTH)
                this.commitmentPeriod = new CommitmentPeriod(12);
            else if (discountType == DiscountType.COMMITMENT_24MONTH)
                this.commitmentPeriod = new CommitmentPeriod(24);
            this.commitmentDiscountAmount = new Money(0);
            this.monthlyCharge = new Money(plan.getBasicMonthlyCharge());
        }

        public String getPlanId() {
            return plan.getPlanId();
        }

        public int getBasicMonthlyCharge() {
            return basicMonthlyCharge.getValue();
        }

        public int getCommitmentPeriod() {
            return commitmentPeriod.value();
        }

        public int getCommitmentDiscountAmount() {
            return commitmentDiscountAmount.getValue();
        }

        private void setCommitmentDiscountAmount(int amount) {
            commitmentDiscountAmount = new Money(amount);
            monthlyCharge = basicMonthlyCharge.minus(commitmentDiscountAmount);
        }

        public int getMonthlyCharge() {
            return monthlyCharge.getValue();
        }
    }
}
