package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Account;
import com.github.snowhite93.bankingapp.model.Transactions;

import java.util.List;

public interface TransactionsService {
    public List<Transactions> findAllTransactionsForAccId(int accountId) throws BankingAppSystemException;
    public void rejectTransaction(int userId) throws BankingAppException;
    public void approveTransaction(int userId) throws BankingAppException;
    public void createTransaction(int fromAccId, int toAccId, double amount) throws BankingAppException;
}
