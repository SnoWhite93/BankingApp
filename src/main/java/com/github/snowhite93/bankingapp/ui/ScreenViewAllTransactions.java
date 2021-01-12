package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.model.Account;
import com.github.snowhite93.bankingapp.model.Transactions;
import com.github.snowhite93.bankingapp.model.User;
import com.github.snowhite93.bankingapp.service.*;
import org.apache.log4j.Logger;

import java.util.List;

public class ScreenViewAllTransactions implements Screen {

    private static final Logger log = Logger.getLogger(ScreenViewAllTransactions.class);
    private TransactionsService transactionsService = new TransactionsServiceImpl();
    private UserService userService = new UserServiceImpl();
    private AccountService accountService = new AccountServiceImpl();


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
            Account fromAccount = accountService.findAccountByAccID(transaction.getFromAccountId());
            Account toAccount = accountService.findAccountByAccID(transaction.getToAccountId());
            User fromUser = userService.findUserByUserId(fromAccount.getUserId());
            User toUser = userService.findUserByUserId(toAccount.getUserId());

            log.info("Transaction " + transaction.getTransactionId()
                    + " from "
                    + fromUser.getFirstName() + " " + fromUser.getLastName()
                    + " to "
                    + toUser.getFirstName() + " " + toUser.getLastName()
                    + " with an amount of $" + transaction.getAmount()
                    + " status is " + transaction.getStatus());

        }

    }

}
