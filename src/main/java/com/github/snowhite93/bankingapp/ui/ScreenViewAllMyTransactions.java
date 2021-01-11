package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.model.Account;
import com.github.snowhite93.bankingapp.model.Transactions;
import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.service.AccountService;
import com.github.snowhite93.bankingapp.service.AccountServiceImpl;
import com.github.snowhite93.bankingapp.service.TransactionsService;
import com.github.snowhite93.bankingapp.service.TransactionsServiceImpl;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ScreenViewAllMyTransactions implements Screen {

    private static final Logger log = Logger.getLogger(ScreenViewAllMyTransactions.class);
    private final User user;

    private AccountService accountService = new AccountServiceImpl();
    private TransactionsService transactionsService = new TransactionsServiceImpl();

    public ScreenViewAllMyTransactions(User user) {
        this.user = user;
    }

    @Override
    public void showScreen() {
        log.info("---------------------------------------");
        List<Account> accountsForUser = accountService.retrieveAllAccountsForUserId(user.getUserId());
        List<Transactions> pendingTransactions = getPendingTransactions(accountsForUser);
        if (pendingTransactions.isEmpty()) {
            log.info("You have no transactions.");
            return;
        }

        log.info("You have the following incoming pending transactions: ");
        for (Transactions transaction : pendingTransactions) {
            log.info("Transaction " + transaction.getTransactionId() + " with an amount of $" + transaction.getAmount() + " status is " + transaction.getStatus());
        }

    }

    private List<Transactions> getPendingTransactions(List<Account> accountsForUser) {
        List<Transactions> transactions = new ArrayList<>();
        for (Account account : accountsForUser) {
            List<Transactions> accountTransactions = transactionsService.findAllTransactionsForAccId(account.getAccountId());
            transactions.addAll(accountTransactions);
        }
        return transactions;
    }

}
