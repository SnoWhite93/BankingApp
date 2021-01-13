package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.Account;
import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.service.AccountService;
import com.github.snowhite93.bankingapp.service.AccountServiceImpl;
import org.apache.log4j.Logger;

import java.util.List;

import static com.github.snowhite93.bankingapp.ui.Inputs.readDouble;
import static com.github.snowhite93.bankingapp.ui.Inputs.readInt;

public class ScreenMakeAWithdrawl implements Screen {

    private static final Logger log = Logger.getLogger(ScreenMakeAWithdrawl.class);
    private final User user;

    private Input input = InputScanner.getInstance();
    private AccountService accountService = new AccountServiceImpl();

    public ScreenMakeAWithdrawl(User user) {
        this.user = user;
    }

    @Override
    public void showScreen() {
        log.info("---------------------------------------");
        List<Account> accountsForUser = accountService.retrieveAllAccountsForUserId(user.getUserId());

        if (accountsForUser.size() == 0) {
            log.info("There are no accounts for this user name, you have to register for a account.");
            return;
        }

        log.info("You have the following accounts: ");
        for (Account account : accountsForUser) {
            log.info(account.getAccountId() + " - $" + account.getBalance());
        }

        Account account = getAccount(accountsForUser);
        if (account != null) {

            log.info("Enter amount to withdraw: ");
            double amount = readDouble(input);
            try {
                accountService.withdraw(user.getUserId(), account.getAccountId(), amount);
                log.info("Successful withdraw!");
            } catch (BankingAppException e) {
                log.error(e.getMessage());
            }
        }
    }

    private Account getAccount(List<Account> accountsForUser) {
        if (accountsForUser.size() == 1) {
            return accountsForUser.get(0);
        }

        log.info("Please enter the account id for the account that you want to use: ");
        int accountId = readInt(input);
        try {
            return accountService.findAccountByAccID(accountId);
        } catch (BankingAppUserException e) {
            log.error(e.getMessage());
            return null;
        }
    }


}
