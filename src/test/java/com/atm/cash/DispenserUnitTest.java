package com.atm.cash;

import com.atm.exception.InsufficientCashException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.atm.cash.DispenserUnit.FIVE_HUNDRED_NOTE_COUNT;
import static com.atm.cash.DispenserUnit.HUNDRED_NOTE_COUNT;
import static com.atm.cash.DispenserUnit.THOUSAND_NOTE_COUNT;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DispenserUnitTest {

    public static final long REQUESTED_CASH = 1000l;
    private DispenserUnit dispenserForTest;

    @Before
    public void setUp() throws Exception {
        dispenserForTest = DispenserUnit.getInstance();
    }

    @Test
    public void shouldInitializeDispenserWithCash() throws Exception {
        Cash cashInDispenser = dispenserForTest.getCashInDispenser();
        assertEquals("Incorrect Thousand currency notes initialized", THOUSAND_NOTE_COUNT, cashInDispenser.get(CurrencyType.THOUSAND));
        assertTrue("Five Hundred currency notes not initialized", cashInDispenser.containsKey(CurrencyType.FIVE_HUNDRED));
    }

    @Test
    public void shouldDispenseCashOnDemand() throws Exception {
        DispenseChain mockDispenseChain = mock(DispenseChain.class);
        dispenserForTest.setRootDispenser(mockDispenseChain);
        Cash expectedValue = new Cash();
        Long requestedCash = REQUESTED_CASH;
        when(mockDispenseChain.dispense(any(Cash.class),eq(dispenserForTest.getCashInDispenser()),eq(requestedCash))).thenReturn(expectedValue);
        Cash actualCash = dispenserForTest.dispenseCash(requestedCash);
        assertSame("Incorrect cash returned",expectedValue, actualCash);
    }

    @Test(expected = InsufficientCashException.class)
    public void shouldThrowExceptionWhenRequestedCashMoreThanAtmCash() throws Exception {
        Long requestedCash = dispenserForTest.getCashInDispenser().getTotalValue()+1;
        dispenserForTest.dispenseCash(requestedCash);
    }

}