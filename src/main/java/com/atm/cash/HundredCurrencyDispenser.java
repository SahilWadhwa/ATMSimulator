package com.atm.cash;

import com.atm.exception.InsufficientCashException;

import static com.atm.cash.CurrencyType.HUNDRED;

public class HundredCurrencyDispenser implements DispenseChain {

    @Override
    public void setNext(DispenseChain nextChain) {
    }

    @Override
    public Cash dispense(final Cash cashDispensed,final Cash cashInATM, Long cashToDispense) {
        if (cashToDispense >= HUNDRED.getValue()) {
            int notes = (int) (cashToDispense / HUNDRED.getValue());

            if (cashInATM.get(HUNDRED) >= notes) {
                cashToDispense = (cashToDispense % HUNDRED.getValue());
                cashInATM.removeCurrency(HUNDRED, notes);
                cashDispensed.addCurrency(HUNDRED, notes);
            }
            if (cashToDispense == 0) {
                return cashDispensed;
            }
        }
        throw new InsufficientCashException();


    }
}
