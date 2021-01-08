package com.github.snowhite93.bankingapp.dao;

import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Transactions;

import java.util.List;

public interface TransactionsDAO {

    public List<Transactions> allTransactionsWithAccId(int accountId) throws BankingAppSystemException;
    public boolean cancelTransaction(int userId) throws BankingAppSystemException;
    public boolean acceptTransaction(int userId) throws BankingAppSystemException;
    public boolean createTransaction(int fromAccId, int toAccId, double amount) throws BankingAppSystemException;
}
