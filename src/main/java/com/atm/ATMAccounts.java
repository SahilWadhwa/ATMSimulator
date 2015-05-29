package com.atm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMAccounts {
    private Map<Integer, Integer> cardPinMap = new HashMap<Integer, Integer>();
    private Map<Integer, Long> cardBalanceMap = new HashMap<Integer, Long>();

    String csvFile = "account-details.csv";
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";


    public ATMAccounts() {
        loadATMAccountsFromCSVFile();
    }

    public ATMAccounts(List<String> csvAccountDetails) {
        populateAccountDetailsFromCSV(csvAccountDetails);
    }

    public Boolean authenticateCredentials(Integer cardNumber, Integer pin) {
        return cardPinMap.containsKey(cardNumber) && cardPinMap.get(cardNumber).equals(pin);
    }

    public Boolean isValidCard(Integer card) {
        return cardPinMap.containsKey(card);
    }

    public Long debitAmount(Integer card, Long amountToDebit) {
        Long currentBalance = cardBalanceMap.get(card);
        cardBalanceMap.put(card, currentBalance - amountToDebit);
        return getBalanceFor(card);
    }

    public Long getBalanceFor(Integer card) {
        if (cardBalanceMap.containsKey(card))
            return cardBalanceMap.get(card);
        else
            return 0l;
    }

    private void loadATMAccountsFromCSVFile() {
        try {
            br = new BufferedReader(new FileReader(csvFile));
            ArrayList<String> commaSeparatedValues = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                commaSeparatedValues.add(line);
            }
            populateAccountDetailsFromCSV(commaSeparatedValues);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void populateAccountDetailsFromCSV(List<String> csv) {
        for (String line : csv) {
            if (line.length() > 3) {
                String[] accountDetails = line.split(cvsSplitBy);
                int cardNumber = Integer.parseInt(accountDetails[0]);
                cardPinMap.put(cardNumber, Integer.parseInt(accountDetails[1]));
                cardBalanceMap.put(cardNumber, Long.parseLong(accountDetails[2]));
            }
        }
    }


}
