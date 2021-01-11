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

public class ScreenViewAllTransactions implements Screen {

    private static final Logger log = Logger.getLogger(ScreenViewAllTransactions.class);
    private TransactionsService transactionsService = new TransactionsServiceImpl();


    @Override
    public void showScreen() {
        log.info("---------------------------------------");
        List<Transactions> allTransactionsList = transactionsService.findAllTransactions();
        if (allTransactionsList.isEmpty()) {
            log.info("There are no transactions to show");
            return;
        }

        log.info("Showing all transactions for every user: ");
        for (Transactions transaction : allTransactionsList) {
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
