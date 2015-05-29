package com.atm.cash;

import com.atm.exception.InsufficientCashException;
import org.junit.Before;
import org.junit.Test;

import static com.atm.cash.CurrencyType.HUNDRED;
import static com.atm.cash.DispenserUnit.HUNDRED_NOTE_COUNT;
import static org.junit.Assert.assertTrue;

public class HundredCurrencyDispenserTest {

    private HundredCurrencyDispenser classUnderTest;
    private Cash cashWithAtm;
    private Long cashInLimit;
    private Long cashOutsideLimit;

    @Before
    public void setUp() throws Exception {
        classUnderTest = new HundredCurrencyDispenser();
        cashWithAtm = DispenserUnit.getInstance().getCashInDispenser();
        cashInLimit = Long.valueOf(HUNDRED.getValue() * HUNDRED_NOTE_COUNT);
        cashOutsideLimit = Long.valueOf(HUNDRED.getValue() * (HUNDRED_NOTE_COUNT + 10));
    }

    @Test
    public void shouldDispenseAmountInMultipleOfHundred() throws Exception {
        Cash cashDispensed = classUnderTest.dispense(new Cash(), cashWithAtm, cashInLimit);
        assertTrue("Incorrect Hundred currency notes initialized", cashDispensed.get(HUNDRED).equals(HUNDRED_NOTE_COUNT));
    }

    @Test(expected = InsufficientCashException.class)
    public void shouldThrowExceptionIfLessCurrencyNotesAvailable() throws Exception {
        classUnderTest.dispense(new Cash(), cashWithAtm, cashOutsideLimit);
    }

    @Test(expected = InsufficientCashException.class)
    public void shouldThrowExceptionIfCashRequiredIsLessThan100() throws Exception {
        classUnderTest.dispense(new Cash(), cashWithAtm, 99L);
    }

}