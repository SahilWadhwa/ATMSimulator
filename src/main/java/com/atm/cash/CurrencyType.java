package com.atm.cash;

public enum CurrencyType {
    HUNDERED(100), FIVE_HUNDERED(500), THOUSAND(1000);

    private Integer value;

    CurrencyType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
