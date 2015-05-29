package com.atm;

import com.atm.cash.DispenserUnit;
import com.atm.exception.InsufficientCashException;

import java.util.Scanner;

public class ATMachine {
    private static ATMachine atm = new ATMachine();
    private ATMAccounts accounts = new ATMAccounts();
    private DispenserUnit dispenserUnit;
    Scanner in;


    public static ATMachine getATMInstance() {
        return atm;
    }


    private ATMachine() {
        dispenserUnit = DispenserUnit.getInstance();
        in = new Scanner(System.in);
    }

    public void bootUp() {
        Integer card = enterCardAndPin();
        withdrawCash(card);
        ejectCard(in);

    }

    private Integer enterCardAndPin() {
        System.out.println("Enter Card/acc number");
        Integer card = Integer.parseInt(in.nextLine());
        if (!accounts.isValidCard(card)) {
            System.out.println("Invalid Card.");
            ejectCard(in);
        }

        System.out.println("Enter Pin number");
        Integer pin = Integer.parseInt(in.nextLine());
        if (!accounts.authenticateCredentials(card, pin)) {
            System.out.println("Invalid Pin");
            ejectCard(in);
        }
        return card;
    }

    private void withdrawCash(Integer card) {
        System.out.println("Enter Cash To Withdraw");
        Long cashRequested = Long.parseLong(in.nextLine());
        try {
            if (accounts.getBalanceFor(card) > cashRequested) {
                System.out.println("Cash Received -" + dispenserUnit.dispenseCash(cashRequested).getBalanceSummary());
                System.out.println("Remaining Account Balance " + accounts.debitAmount(card, cashRequested));
            } else {
                System.out.printf("In sufficient balance in account");
            }
        } catch (InsufficientCashException e) {
            System.out.println("Insufficient funds in ATM, Please try later");
        }
    }

    public void ejectCard(Scanner in) {

        System.out.println("Your card had been ejected.");
        System.out.println("Do you want to do another transaction- y/n");
        if (in.nextLine().equalsIgnoreCase("y")) {
            this.bootUp();
        }
        System.out.println("Thank you!!  visit again");


    }

    @Override
    protected void finalize() throws Throwable {
        in.close();

    }
}
