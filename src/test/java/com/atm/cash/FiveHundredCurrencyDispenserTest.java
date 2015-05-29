package com.atm.cash;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.atm.cash.CurrencyType.FIVE_HUNDRED;
import static com.atm.cash.DispenserUnit.FIVE_HUNDRED_NOTE_COUNT;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FiveHundredCurrencyDispenserTest {

    private FiveHundredCurrencyDispenser classUnderTest;
    private Cash cashWithAtm;
    private Long cashInLimit;
    private Long cashOutsideLimit;

    @Before
    public void setUp() throws Exception {
        classUnderTest = new FiveHundredCurrencyDispenser();
        cashWithAtm = DispenserUnit.getInstance().getCashInDispenser();
        cashInLimit = Long.valueOf(FIVE_HUNDRED.getValue() * FIVE_HUNDRED_NOTE_COUNT);
        cashOutsideLimit = Long.valueOf(FIVE_HUNDRED.getValue() * (FIVE_HUNDRED_NOTE_COUNT + 10));
    }

    @Test
    public void shouldDispenseAmountInMultipleOfFiveHundred() throws Exception {
        Cash cashDispensed = classUnderTest.dispense(new Cash(), cashWithAtm, cashInLimit);
        assertTrue("Incorrect Five Hundred currency notes initialized", cashDispensed.get(FIVE_HUNDRED).equals(10));
    }

    @Test
    public void shouldPassOnToNextDispenseChainIfLessCurrencyNotesAvailable() throws Exception {
        DispenseChain mockNextDispenser = mock(DispenseChain.class);
        classUnderTest.setNext(mockNextDispenser);
        Cash cashDispensed = new Cash();
        Cash expectedCashValue = new Cash();
        when(mockNextDispenser.dispense(cashDispensed, cashWithAtm, cashOutsideLimit)).thenReturn(expectedCashValue);
        cashDispensed = classUnderTest.dispense(cashDispensed, cashWithAtm, cashOutsideLimit);
        assertSame("Incorrect Cash dispensed", expectedCashValue, cashDispensed);
        verify(mockNextDispenser).dispense(cashDispensed, cashWithAtm, cashOutsideLimit);
    }

    @Test
    public void shouldPassOnToNextDispenseChainIfCashRequiredIsLessThan500() throws Exception {
        DispenseChain mockNextDispenser = mock(DispenseChain.class);
        classUnderTest.setNext(mockNextDispenser);
        Cash cashDispensed = new Cash();
        Cash expectedCashValue = new Cash();
        when(mockNextDispenser.dispense(cashDispensed, cashWithAtm, 100L)).thenReturn(expectedCashValue);
        cashDispensed = classUnderTest.dispense(cashDispensed, cashWithAtm, 100L);
        assertSame("Incorrect Cash dispensed", expectedCashValue, cashDispensed);
        verify(mockNextDispenser).dispense(cashDispensed, cashWithAtm, 100L);
    }
}