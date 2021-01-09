package com.github.snowhite93.bankingapp.ui;

import org.apache.log4j.Logger;

public class ScreenLoggedIn implements Screen {

    private static final Logger log = Logger.getLogger(ScreenLoggedIn.class);
    private Input input = InputScanner.getInstance();

    // The screen that shows up when logged in to the account

    @Override
    public void showScreen() {
        while (true) {
            log.info("---------------------------------------");
            log.info("Welcome to BankAholic!");
            log.info("Select from the following options: ");
            log.info("1) Apply for a new bank account ");
            log.info("2) Make a withdrawl from your account ");
            log.info("3) Deposit money to your account ");
            log.info("4) Make money transfer");
            log.info("5) Accept money transfer ");
            log.info("6) View my transactions from account");
            log.info("0) Log out ");

            String option = input.readLine();
            switch (option) {
                case "1":
                    log.info("Create a new account");
                    break;
                case "2":
                    log.info("Make a withdrawl");
                    break;
                case "3":
                    log.info("Deposit some money");
                    break;
                case "4":
                    log.info("Make a money transfer");
                    break;
                case "5":
                    log.info("Accept money transfer");
                    break;
                case "6":
                    log.info("View my transactions");
                    break;
                case "0":
                    return; //exit app
                default:
                    log.error("Invalid option: " + option);
                    break;
            }
        }
    }


}
