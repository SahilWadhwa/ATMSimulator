package com.atm;

import com.atm.cash.DispenserUnit;
import com.atm.exception.InsufficientCashException;

import java.util.Scanner;

public class ATMachine {
    private static ATMachine atm = new ATMachine();
    private final ATMAccounts accounts = new ATMAccounts();
    private final DispenserUnit dispenserUnit;
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
            System.out.println("Invalid Card. Exiting!!");
            System.exit(0);
        }

        System.out.println("Enter Pin number");
        Integer pin = Integer.parseInt(in.nextLine());
        if (!accounts.authenticateCredentials(card, pin)) {
            System.out.println("Invalid Pin. Exiting!!");
            System.exit(0);
        }
        return card;
    }

    private void withdrawCash(Integer card) {
        System.out.println("Enter Cash To Withdraw in multiple of 100");
        Long cashRequested = Long.parseLong(in.nextLine());
        if(cashRequested%100!=0) {System.out.println("Entered amount was not in multiple of 100. Exiting!!"); System.exit(0);}
        try {
            if (accounts.getBalanceFor(card) > cashRequested) {
                System.out.println("Cash Received.     " + dispenserUnit.dispenseCash(cashRequested).getBalanceSummary());
                System.out.println("Remaining Account Balance Rs" + accounts.debitAmount(card, cashRequested));
            } else {
                System.out.printf("In sufficient balance in account");
            }
        } catch (InsufficientCashException e) {
            System.out.println("Insufficient funds in ATM, Please try later");
        }
    }

    public void ejectCard(Scanner in) {

        System.out.println("Your card had been ejected.\n");
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
