package com.atm.cash;

public enum CurrencyType {
    HUNDRED(100), FIVE_HUNDRED(500), THOUSAND(1000);

    private Integer value;

    CurrencyType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
