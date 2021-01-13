package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.model.Account;
import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.service.AccountService;
import com.github.snowhite93.bankingapp.service.AccountServiceImpl;
import org.apache.log4j.Logger;

import java.util.List;

public class ScreenViewAccountBalances implements Screen {

    private static final Logger log = Logger.getLogger(ScreenViewAccountBalances.class);
    private final User user;

    private AccountService accountService = new AccountServiceImpl();

    public ScreenViewAccountBalances(User user) {
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
            log.info("Account " + account.getAccountId() + " with a balance of $" + account.getBalance());
        }
    }

}
