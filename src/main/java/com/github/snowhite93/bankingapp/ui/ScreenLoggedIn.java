package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.model.User;
import org.apache.log4j.Logger;

public class ScreenLoggedIn implements Screen {

    private static final Logger log = Logger.getLogger(ScreenLoggedIn.class);
    private final User user;
    private Input input = InputScanner.getInstance();

    public ScreenLoggedIn(User user) {
        this.user = user;
    }

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
            log.info("7) View my account balances");
            if (user.isEmployee()) {
                log.info("8) Employee options");
            }
            log.info("0) Log out ");

            String option = input.readLine();
            switch (option) {
                case "0":
                    return;
                case "1":
                    new ScreenApplyForAccStartingBalance(user)
                            .showScreen();
                    break;
                case "2":
                    new ScreenMakeAWithdrawl(user)
                            .showScreen();
                    break;
                case "3":
                    new ScreenMakeADeposit(user)
                            .showScreen();
                    break;
                case "4":
                    new ScreenMakeAMoneyTransfer(user)
                            .showScreen();
                    break;
                case "5":
                    new ScreenAcceptMoneyTransfer(user)
                            .showScreen();
                    break;
                case "6":
                    new ScreenViewAllMyTransactions(user)
                            .showScreen();
                    break;
                case "7":
                    new ScreenViewAccountBalances(user)
                            .showScreen();
                    break;
                case "8":
                    if (user.isEmployee()) {
                        new ScreenEmployeeOptions()
                                .showScreen();
                        break;
                    }
                default:
                    log.error("Invalid option: " + option);
                    break;
            }
        }
    }


}
