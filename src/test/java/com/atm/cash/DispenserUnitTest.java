package com.atm.cash;

import com.atm.exception.InsufficientCashException;
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

    @Test
    public void shouldInitializeDispenserWithCash() throws Exception {
        dispenserForTest = DispenserUnit.getInstance();
        Cash cashInDispenser = dispenserForTest.getCashInDispenser();
        assertTrue("Incorrect Thousand currency notes initialized", cashInDispenser.get(CurrencyType.THOUSAND).equals(THOUSAND_NOTE_COUNT));
        assertTrue("Incorrect Five Hundred currency notes initialized", cashInDispenser.get(CurrencyType.FIVE_HUNDERED).equals(FIVE_HUNDRED_NOTE_COUNT));
        assertTrue("Incorrect Hundred currency notes initialized", cashInDispenser.get(CurrencyType.HUNDERED).equals(HUNDRED_NOTE_COUNT));
    }

    @Test
    public void shouldDispenseCashOnDemand() throws Exception {
        dispenserForTest = DispenserUnit.getInstance();
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
        dispenserForTest = DispenserUnit.getInstance();
        Long requestedCash = dispenserForTest.getCashInDispenser().getTotalValue()+1;
        dispenserForTest.dispenseCash(requestedCash);
    }

}