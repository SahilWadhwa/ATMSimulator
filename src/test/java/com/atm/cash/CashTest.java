package com.atm.cash;

import org.junit.Before;
import org.junit.Test;

import static com.atm.cash.Cash.*;
import static com.atm.cash.CurrencyType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CashTest {

    private Cash cashUnderTest;

    @Before
    public void setUp() throws Exception {
        cashUnderTest = new Cash();
    }

    @Test
    public void shouldAddCurrencyToTotalCash() throws Exception {
        cashUnderTest.addCurrency(THOUSAND, 2);
        assertTrue("Currency was not added to cash", cashUnderTest.containsKey(THOUSAND));
        assertTrue("Currency Notes were not added to total cash", cashUnderTest.get(THOUSAND).equals(2));
    }

    @Test
    public void shouldRemoveCurrencyFromTotalCash() throws Exception {
        cashUnderTest.addCurrency(THOUSAND, 2);
        cashUnderTest.removeCurrency(THOUSAND, 1);
        assertTrue("All Currency notes got deleted", cashUnderTest.containsKey(THOUSAND));
        assertTrue("Currency Notes were not deleted", cashUnderTest.get(THOUSAND).equals(1));
    }

    @Test
    public void shouldReturnTotalValueOfCash() throws Exception {
        cashUnderTest.addCurrency(THOUSAND, 1);
        cashUnderTest.addCurrency(FIVE_HUNDRED, 1);
        cashUnderTest.addCurrency(HUNDRED, 1);
        assertTrue("Incorrect Total value is returned", cashUnderTest.getTotalValue().equals(1600L));
    }

    @Test
    public void shouldReturnCorrectSummary() throws Exception {
        cashUnderTest.addCurrency(THOUSAND, 1);
        String expectedSummary = S1 + cashUnderTest.getTotalValue() + S2 + S6 + THOUSAND.getValue() + S3 + "1" + S4 + S5;
        assertEquals("Incorrect summary was returned", expectedSummary, cashUnderTest.getBalanceSummary());
    }
}