package com.atm.cash;

import static com.atm.cash.CurrencyType.FIVE_HUNDERED;

public class FiveHundredCurrencyDispenser implements DispenseChain {
    DispenseChain next;

    @Override
    public void setNext(DispenseChain nextChain) {
        this.next = nextChain;
    }

    @Override
    public Cash dispense(Cash cashDispensed, Cash cashInATM, Long cashToDispense) {
        if (cashToDispense >= FIVE_HUNDERED.getValue()) {
            int notes = (int) (cashToDispense / FIVE_HUNDERED.getValue());
            if (cashInATM.get(FIVE_HUNDERED) >= notes) {
                cashToDispense = -cashToDispense % FIVE_HUNDERED.getValue();
                cashInATM.removeCurrency(FIVE_HUNDERED, notes);
                cashDispensed.addCurrency(FIVE_HUNDERED, notes);
            }
            if (cashToDispense == 0) {
                return cashDispensed;
            }
        }
        return next.dispense(cashDispensed, cashInATM, cashToDispense);
    }
}
