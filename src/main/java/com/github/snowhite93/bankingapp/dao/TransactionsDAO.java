package com.github.snowhite93.bankingapp.dao;

import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Transactions;

import java.util.List;

public interface TransactionsDAO {

    public List<Transactions> allTransactionsWithAccId(int accountId) throws BankingAppSystemException;

    public List<Transactions> findIncomingPendingTransactions(int accountId) throws BankingAppSystemException;

    public boolean cancelTransaction(int transactionId) throws BankingAppSystemException;

    public boolean acceptTransaction(int transactionId) throws BankingAppSystemException;

    public Transactions findTransactionById(int transactionId) throws BankingAppSystemException;

    public boolean createTransaction(int fromAccId, int toAccId, double amount) throws BankingAppSystemException;

    public List<Transactions> allTransactions() throws BankingAppSystemException;

}
