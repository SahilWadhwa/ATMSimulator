package com.atm.cash;

import static com.atm.cash.CurrencyType.FIVE_HUNDRED;

public class FiveHundredCurrencyDispenser implements DispenseChain {
    DispenseChain next;

    @Override
    public void setNext(DispenseChain nextChain) {
        this.next = nextChain;
    }

    @Override
    public Cash dispense(final Cash cashDispensed,final Cash cashInATM, Long cashToDispense) {
        if (cashToDispense >= FIVE_HUNDRED.getValue()) {
            int notes = (int) (cashToDispense / FIVE_HUNDRED.getValue());
            if (cashInATM.get(FIVE_HUNDRED) >= notes) {
                cashToDispense = cashToDispense % FIVE_HUNDRED.getValue();
                cashInATM.removeCurrency(FIVE_HUNDRED, notes);
                cashDispensed.addCurrency(FIVE_HUNDRED, notes);
            }
            if (cashToDispense == 0) {
                return cashDispensed;
            }
        }
        return next.dispense(cashDispensed, cashInATM, cashToDispense);
    }
}
