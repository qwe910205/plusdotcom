package com.qwe910205.plusdotcom.payment.domain;

public enum DiscountType {
    NONE("없음"),
    PUBLICLY_SUBSIDY("공시지원금"),
    COMMITMENT_24MONTH("선택약정24개월"),
    COMMITMENT_12MONTH("선택약정12개월");

    private final String name;

    DiscountType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
