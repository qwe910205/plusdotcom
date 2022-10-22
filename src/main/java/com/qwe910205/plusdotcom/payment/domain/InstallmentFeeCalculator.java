package com.qwe910205.plusdotcom.payment.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.Price;

public class InstallmentFeeCalculator {

    private final static double ANNUAL_INTEREST_RATE = 0.059;

    public static Price calculate(int installmentPeriod, int installmentPrinciple) {
        if (installmentPeriod < 12)
            return new Price(0);

        double monthlyInterestRate = ANNUAL_INTEREST_RATE / 12;
        double monthlyBilledAmount = installmentPrinciple * monthlyInterestRate * (Math.pow((1 + monthlyInterestRate), installmentPeriod) / (Math.pow((1 + monthlyInterestRate), installmentPeriod) - 1));
        double totalInstallment = monthlyBilledAmount * installmentPeriod;
        return new Price((int) Math.round(totalInstallment - installmentPrinciple));
    }
}
