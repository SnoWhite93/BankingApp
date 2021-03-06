package com.github.snowhite93.bankingapp.ui;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
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

import static com.github.snowhite93.bankingapp.ui.Inputs.readInt;

public class ScreenAcceptMoneyTransfer implements Screen {

    private static final Logger log = Logger.getLogger(ScreenAcceptMoneyTransfer.class);
    private final User user;

    private Input input = InputScanner.getInstance();
    private AccountService accountService = new AccountServiceImpl();
    private TransactionsService transactionsService = new TransactionsServiceImpl();

    public ScreenAcceptMoneyTransfer(User user) {
        this.user = user;
    }

    @Override
    public void showScreen() {
        log.info("---------------------------------------");
        List<Account> accountsForUser = accountService.retrieveAllAccountsForUserId(user.getUserId());
        List<Transactions> pendingTransactions = getPendingTransactions(accountsForUser);
        if (pendingTransactions.isEmpty()) {
            log.info("You have no pending transactions.");
            return;
        }

        log.info("You have the following incoming pending transactions: ");
        for (Transactions pendingTransaction : pendingTransactions) {
            log.info("Transaction id " + pendingTransaction.getTransactionId() + " with an amount of $" + pendingTransaction.getAmount());
        }

        log.info("Input transaction id to accept:");
        int transaction = readInt(input);

        try {
            transactionsService.approveTransaction(user.getUserId(), transaction);
            log.info("Approved transaction!");
        } catch (BankingAppException e) {
            log.error(e.getMessage());
        }
    }

    private List<Transactions> getPendingTransactions(List<Account> accountsForUser) {
        List<Transactions> transactions = new ArrayList<>();
        for (Account account : accountsForUser) {
            List<Transactions> accountTransactions = transactionsService.findIncomingPendingTransactions(account.getAccountId());
            transactions.addAll(accountTransactions);
        }
        return transactions;
    }
}
