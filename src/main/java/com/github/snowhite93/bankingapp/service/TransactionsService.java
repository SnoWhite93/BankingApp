package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Transactions;

import java.util.List;

public interface TransactionsService {

    public List<Transactions> findAllTransactionsForAccId(int accountId) throws BankingAppSystemException;

    public List<Transactions> findIncomingPendingTransactions(int accountId) throws BankingAppSystemException;

    public void rejectTransaction(int userId, int transactionId) throws BankingAppException;

    public void approveTransaction(int userId, int transactionId) throws BankingAppException;

    public void createTransaction(int userId, int fromAccId, int toAccId, double amount) throws BankingAppException;

    public List<Transactions> findAllTransactions() throws BankingAppSystemException;

}
