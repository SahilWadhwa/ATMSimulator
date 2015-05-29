package com.atm;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ATMAccountsTest {

    private ATMAccounts classUnderTest;

    @Before
    public void setUp() throws Exception {
        List<String> accountDetailsCsv = Arrays.asList("111,222,300", "666,777,800");
        classUnderTest = new ATMAccounts(accountDetailsCsv);
    }

    @Test
    public void testShouldAuthenticateCredentials() throws Exception {
        assertTrue("Credentials not authenticated", classUnderTest.authenticateCredentials(111, 222));
    }

    @Test
    public void shouldGetAccountBalanceForAccount() throws Exception {
        assertTrue("Incorrect Account Balance Returned", classUnderTest.getBalanceFor(111).equals(300l));
    }

    @Test
    public void shouldValidateExistingCard() throws Exception {
        assertTrue("Card is not validated", classUnderTest.isValidCard(111));
    }

    @Test
    public void shouldDebitFromAccount() throws Exception {
        classUnderTest.debitAmount(111, 100l);
        assertTrue("Account not debited", classUnderTest.getBalanceFor(111).equals(200l));

    }
}