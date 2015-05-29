package com.atm.cash;

public interface DispenseChain {
    void setNext(DispenseChain nextChain);

    Cash dispense(final Cash cashDispensed, final Cash cashInATM, Long cashToDispense);
}
