package com.atm.cash;

import com.atm.exception.InsufficientCashException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DispenserUnitTest {

    private DispenserUnit dispenserForTest;

    @Test
    public void shouldInitializeDispenserWithCash() throws Exception {
        dispenserForTest = DispenserUnit.getInstance();
        Cash cashInDispenser = dispenserForTest.getCashInDispenser();
        assertTrue("Incorrect Thousand currency notes initialized", cashInDispenser.get(CurrencyType.THOUSAND).equals(10));
        assertTrue("Incorrect Five Hundred currency notes initialized", cashInDispenser.get(CurrencyType.FIVE_HUNDERED).equals(10));
        assertTrue("Incorrect Hundred currency notes initialized", cashInDispenser.get(CurrencyType.HUNDERED).equals(10));
    }

    @Test
    public void shouldDispenseCashOnDemand() throws Exception {
        dispenserForTest = DispenserUnit.getInstance();
        DispenseChain mockDispenseChain = mock(DispenseChain.class);
        dispenserForTest.setRootDispenser(mockDispenseChain);
        Cash expectedValue = new Cash();
        Long requestedCash = 1000l;
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