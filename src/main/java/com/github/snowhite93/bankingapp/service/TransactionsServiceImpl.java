package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.AccountDAO;
import com.github.snowhite93.bankingapp.dao.TransactionsDAO;
import com.github.snowhite93.bankingapp.dao.impl.AccountDAOImpl;
import com.github.snowhite93.bankingapp.dao.impl.TransactionsDAOImpl;
import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.Account;
import com.github.snowhite93.bankingapp.model.Transactions;

import java.util.List;

public class TransactionsServiceImpl implements TransactionsService {

    private TransactionsDAO transactionsDAO = new TransactionsDAOImpl();
    private AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public List<Transactions> findAllTransactionsForAccId(int accountId) throws BankingAppSystemException {
        List<Transactions> allTransactionsForAccIdList = null;
        if (accountId == 0) {
            throw new BankingAppSystemException("Could not retrieve transactions for account id " + accountId);
        }
        allTransactionsForAccIdList = transactionsDAO.allTransactionsWithAccId(accountId);
        return allTransactionsForAccIdList;
    }

    @Override
    public List<Transactions> findIncomingPendingTransactions(int accountId) throws BankingAppSystemException {
        Account account = accountDAO.findAccountByAccId(accountId);
        if (account == null) {
            throw new BankingAppUserException("No such account: " + accountId);
        }
        return transactionsDAO.findIncomingPendingTransactions(accountId);
    }

    @Override
    public void rejectTransaction(int userId, int transactionId) throws BankingAppException {
        Transactions transaction = transactionsDAO.findTransactionById(transactionId);
        if (transaction == null) {
            throw new BankingAppUserException("No such transaction: " + transactionId);
        } else if (!"Pending".equals(transaction.getStatus())) {
            throw new BankingAppUserException("Transaction is not pending");
        }
        Account toAccount = accountDAO.findAccountByAccId(transaction.getToAccountId());
        if (toAccount.getUserId() != userId) {
            throw new BankingAppUserException("Can only reject your own incoming transactions");
        }
        boolean rejectedTransaction = transactionsDAO.cancelTransaction(userId);
        if (!rejectedTransaction) {
            throw new BankingAppSystemException("Could not reject transaction made by user id " + userId);
        }
    }

    @Override
    public void approveTransaction(int userId, int transactionId) throws BankingAppException {
        Transactions transaction = transactionsDAO.findTransactionById(transactionId);
        if (transaction == null) {
            throw new BankingAppUserException("No such transaction: " + transactionId);
        } else if (!"Pending".equals(transaction.getStatus())) {
            throw new BankingAppUserException("Transaction is not pending");
        }
        Account toAccount = accountDAO.findAccountByAccId(transaction.getToAccountId());
        if (toAccount.getUserId() != userId) {
            throw new BankingAppUserException("Can only accept your own incoming transactions");
        }
        boolean approvedTransaction = transactionsDAO.acceptTransaction(transactionId);
        if (!approvedTransaction) {
            throw new BankingAppSystemException("Could not approve transaction made by user id " + userId);
        }

        double newBalance = toAccount.getBalance() - transaction.getAmount();
        boolean newBalanceUpdated = accountDAO.updateBalance(toAccount.getAccountId(), newBalance);
        if (!newBalanceUpdated) {
            throw new BankingAppSystemException("Could not update balance");
        }
    }


    @Override
    public void createTransaction(int userId, int fromAccId, int toAccId, double amount) throws BankingAppException {
        Account fromAccount = accountDAO.findAccountByAccId(fromAccId);
        Account toAccount = accountDAO.findAccountByAccId(toAccId);
        if (fromAccount == null) {
            throw new BankingAppUserException("No such account: " + fromAccId);
        } else if (toAccount == null) {
            throw new BankingAppUserException("No such account: " + toAccId);
        } else if (fromAccount.getUserId() != userId) {
            throw new BankingAppUserException("Cannot transfer from someone else's account");
        } else if (amount <= 0) {
            throw new BankingAppSystemException("Cannot transfer $0 or less.");
        } else if (fromAccount.getBalance() < amount) {
            throw new BankingAppUserException("Not enough money!");
        }
        double newBalance = fromAccount.getBalance() - amount;
        boolean newBalanceUpdated = accountDAO.updateBalance(fromAccId, newBalance);
        if (!newBalanceUpdated) {
            throw new BankingAppSystemException("Could not update balance");
        }
        boolean transactionCreated = transactionsDAO.createTransaction(fromAccId, toAccId, amount);
        if (!transactionCreated) {
            throw new BankingAppSystemException("Could not create transaction");
        }
    }

    @Override
    public List<Transactions> findAllTransactions() throws BankingAppSystemException {
        List<Transactions> allTransactionsList = null;
        allTransactionsList = transactionsDAO.allTransactions();
        return allTransactionsList;
    }

}
