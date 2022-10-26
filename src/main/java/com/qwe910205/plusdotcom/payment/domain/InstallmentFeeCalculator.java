package com.qwe910205.plusdotcom.payment.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstallmentFeeCalculator {

    private final static double ANNUAL_INTEREST_RATE = 0.059;

    public static int calculate(int installmentPeriod, int installmentPrinciple) {
        if (installmentPeriod < 12)
            return 0;

        double monthlyInterestRate = ANNUAL_INTEREST_RATE / 12;
        double monthlyBilledAmount = installmentPrinciple * monthlyInterestRate * (Math.pow((1 + monthlyInterestRate), installmentPeriod) / (Math.pow((1 + monthlyInterestRate), installmentPeriod) - 1));
        double totalInstallment = monthlyBilledAmount * installmentPeriod;
        return (int) Math.round(totalInstallment - installmentPrinciple);
    }
}
