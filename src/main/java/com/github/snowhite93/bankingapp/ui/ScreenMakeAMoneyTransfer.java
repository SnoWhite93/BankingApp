package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.Account;
import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.service.AccountService;
import com.github.snowhite93.bankingapp.service.AccountServiceImpl;
import com.github.snowhite93.bankingapp.service.TransactionsService;
import com.github.snowhite93.bankingapp.service.TransactionsServiceImpl;
import org.apache.log4j.Logger;

import java.util.List;

import static com.github.snowhite93.bankingapp.ui.Inputs.readDouble;
import static com.github.snowhite93.bankingapp.ui.Inputs.readInt;

public class ScreenMakeAMoneyTransfer implements Screen {

    private static final Logger log = Logger.getLogger(ScreenMakeAMoneyTransfer.class);
    private final User user;

    private Input input = InputScanner.getInstance();
    private AccountService accountService = new AccountServiceImpl();
    private TransactionsService transactionsService = new TransactionsServiceImpl();

    public ScreenMakeAMoneyTransfer(User user) {
        this.user = user;
    }

    @Override
    public void showScreen() {
        log.info("---------------------------------------");
        List<Account> accountsForUser = accountService.retrieveAllAccountsForUserId(user.getUserId());
        log.info("You have the following accounts: ");
        for (Account account : accountsForUser) {
            log.info("Account " + account.getAccountId() + " with a balance of $" + account.getBalance());
        }

        Account fromAccount = getFromAccount(accountsForUser);
        if (fromAccount == null) {
            return;
        }

        Account toAccount = getToAccount();
        if (toAccount == null) {
            log.error("No such account!");
            return;
        }

        log.info("Input amount to transfer: ");
        double amount = readDouble(input);

        try {
            transactionsService.createTransaction(user.getUserId(), fromAccount.getAccountId(), toAccount.getAccountId(), amount);
            log.info("Successful transaction!");
        } catch (BankingAppException e) {
            log.error(e.getMessage());
        }
    }

    private Account getFromAccount(List<Account> accountsForUser) {
        if (accountsForUser.size() == 0) {
            log.info("There are no accounts for this user name, you have to register for a account.");
            return null;
        }

        if (accountsForUser.size() == 1) {
            return accountsForUser.get(0);
        }

        log.info("Please enter the account id for the account that you want to transfer from: ");
        int accountId = readInt(input);
        try {
            return accountService.findAccountByAccID(accountId);
        } catch (BankingAppUserException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private Account getToAccount() {
        log.info("Please enter the account number where to transfer money: ");
        int accountId = readInt(input);
        try {
            return accountService.findAccountByAccID(accountId);
        } catch (BankingAppUserException e) {
            log.error(e.getMessage());
            return null;
        }
    }


}
