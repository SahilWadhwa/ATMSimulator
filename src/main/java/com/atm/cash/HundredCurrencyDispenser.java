package com.atm.cash;

import com.atm.exception.InsufficientCashException;

import static com.atm.cash.CurrencyType.HUNDERED;

public class HundredCurrencyDispenser implements DispenseChain {

    @Override
    public void setNext(DispenseChain nextChain) {
    }

    @Override
    public Cash dispense(Cash cashDispensed, Cash cashInATM, Long cashToDispense) {
        if (cashToDispense >= HUNDERED.getValue()) {
            int notes = (int) (cashToDispense / HUNDERED.getValue());

            if (cashInATM.get(HUNDERED) >= notes) {
                cashToDispense = -cashToDispense % HUNDERED.getValue();
                cashInATM.removeCurrency(HUNDERED, notes);
                cashDispensed.addCurrency(HUNDERED, notes);
            }
            if (cashToDispense == 0) {
                return cashDispensed;
            }
        }
        throw new InsufficientCashException();


    }
}
