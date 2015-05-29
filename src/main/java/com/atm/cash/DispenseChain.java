package com.atm.cash;

public interface DispenseChain {
    void setNext(DispenseChain nextChain);

    Cash dispense(Cash cashDispensed, Cash cashInATM, Long cashToDispense);
}
