package com.atm.cash;

import java.util.HashMap;

public class Cash extends HashMap<CurrencyType, Integer> {

    public static final String S1 = "Total: Rs";
    public static final String S2 = " (";
    public static final String S3 = "*";
    public static final String S4 = " ";
    public static final String S5 = ")";

    public void addCurrency(CurrencyType currencyType, Integer currencyNoteCount) {
        if (this.containsKey(currencyType)) {
            currencyNoteCount += this.get(currencyType);
        }
        this.put(currencyType, currencyNoteCount);
    }


    public void removeCurrency(CurrencyType currencyType, Integer currencyCountToRemove) {
        if (this.containsKey(currencyType)) {
            Integer currencyCount = this.get(currencyType);
            this.put(currencyType, currencyCount - currencyCountToRemove);
        }
    }

    public Long getTotalValue() {
        Long totalValue = 0l;

        for (CurrencyType currencyType : this.keySet()) {
            totalValue += this.get(currencyType) * currencyType.getValue();
        }
        return totalValue;
    }

    public String getBalanceSummary() {
        String balanceSummary = S1 + getTotalValue() + S2;
        for (CurrencyType currencyType : this.keySet()) {
            balanceSummary += currencyType.getValue() + S3 + this.get(currencyType) + S4;
        }
        balanceSummary += S5;
        return balanceSummary;

    }
}
