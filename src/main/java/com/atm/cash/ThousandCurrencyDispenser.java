package com.atm.cash;

import static com.atm.cash.CurrencyType.THOUSAND;

public class ThousandCurrencyDispenser implements DispenseChain {
    DispenseChain next;

    @Override
    public void setNext(DispenseChain nextChain) {
        next = nextChain;

    }

    @Override
    public Cash dispense(Cash cashDispensed, Cash cashInATM, Long cashToDispense) {

        if (cashToDispense >= THOUSAND.getValue()) {
            int notes = (int) (cashToDispense / THOUSAND.getValue());
            if (cashInATM.get(THOUSAND) >= notes) {
                cashToDispense = -cashToDispense % THOUSAND.getValue();
                cashInATM.removeCurrency(THOUSAND, notes);
                cashDispensed.addCurrency(THOUSAND, notes);
            }
            if (cashToDispense == 0) {
                return cashDispensed;
            }
        }
        return next.dispense(cashDispensed, cashInATM, cashToDispense);
    }
}
