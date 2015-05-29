package com.atm.cash;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.atm.cash.CurrencyType.THOUSAND;
import static com.atm.cash.DispenserUnit.THOUSAND_NOTE_COUNT;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ThousandCurrencyDispenserTest {


    private ThousandCurrencyDispenser classUnderTest;
    private Cash cashWithAtm;
    private Long cashInLimit;
    private Long cashOutsideLimit;

    @Before
    public void setUp() throws Exception {
        classUnderTest = new ThousandCurrencyDispenser();
        cashWithAtm = DispenserUnit.getInstance().getCashInDispenser();
        cashInLimit = Long.valueOf(THOUSAND.getValue() * THOUSAND_NOTE_COUNT);
        cashOutsideLimit = Long.valueOf(THOUSAND.getValue() * (THOUSAND_NOTE_COUNT + 10));
    }

    @Test
    public void shouldDispenseAmountInMultipleOfThousand() throws Exception {
        Cash cashDispensed = classUnderTest.dispense(new Cash(), cashWithAtm, cashInLimit);
        assertTrue("Incorrect Thousand currency notes initialized", cashDispensed.get(THOUSAND).equals(10));
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
    public void shouldPassOnToNextDispenseChainIfCashRequiredIsLessThan1000() throws Exception {
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