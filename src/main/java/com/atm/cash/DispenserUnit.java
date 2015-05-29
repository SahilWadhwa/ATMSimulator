package com.atm.cash;

import com.atm.exception.InsufficientCashException;

import static com.atm.cash.CurrencyType.*;

public class DispenserUnit {
    public static final Integer THOUSAND_NOTE_COUNT = 10;
    public static final Integer FIVE_HUNDRED_NOTE_COUNT = 10;
    public static final Integer HUNDRED_NOTE_COUNT = 10;
    private static DispenserUnit ourInstance = new DispenserUnit();
    private final Cash cashInDispenser = new Cash();

    public static DispenserUnit getInstance() {
        return ourInstance;
    }


    DispenseChain rootDispenser;
    DispenseChain fiveHundredCurrencyDispenser;
    HundredCurrencyDispenser hundredCurrencyDispenser;

    private DispenserUnit() {
        rootDispenser = new ThousandCurrencyDispenser();
        fiveHundredCurrencyDispenser = new FiveHundredCurrencyDispenser();
        hundredCurrencyDispenser = new HundredCurrencyDispenser();

        rootDispenser.setNext(fiveHundredCurrencyDispenser);
        fiveHundredCurrencyDispenser.setNext(hundredCurrencyDispenser);

        cashInDispenser.addCurrency(THOUSAND, THOUSAND_NOTE_COUNT);
        cashInDispenser.addCurrency(FIVE_HUNDERED, FIVE_HUNDRED_NOTE_COUNT);
        cashInDispenser.addCurrency(HUNDERED, HUNDRED_NOTE_COUNT);
    }

    public Cash dispenseCash(Long cashToDispense) {
        if (cashToDispense <= cashInDispenser.getTotalValue())
            return rootDispenser.dispense(new Cash(), cashInDispenser, cashToDispense);
        else
            throw new InsufficientCashException();
    }

    public Cash getCashInDispenser() {
        return cashInDispenser;
    }

    public void setRootDispenser(DispenseChain rootDispenser) {
        this.rootDispenser = rootDispenser;
    }
}
