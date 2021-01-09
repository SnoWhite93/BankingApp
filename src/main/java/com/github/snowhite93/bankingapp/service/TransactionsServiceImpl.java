package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.TransactionsDAO;
import com.github.snowhite93.bankingapp.dao.impl.TransactionsDAOImpl;
import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Transactions;

import java.util.List;

public class TransactionsServiceImpl implements TransactionsService {

    private TransactionsDAO transactionsDAO = new TransactionsDAOImpl();

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
    public void rejectTransaction(int userId) throws BankingAppException {
        boolean rejectedTransaction = transactionsDAO.cancelTransaction(userId);
        if (!rejectedTransaction) {
            throw new BankingAppSystemException("Could not reject transaction made by user id " + userId);
        }

    }

    @Override
    public void approveTransaction(int userId) throws BankingAppException {
        boolean approvedTransaction = transactionsDAO.acceptTransaction(userId);
        if (!approvedTransaction) {
            throw new BankingAppSystemException("Could not approve transaction made user id " + userId);
        }
    }

    @Override
    public void createTransaction(int fromAccId, int toAccId, double amount) throws BankingAppException {
        if (amount == 0 || fromAccId == 0 || toAccId == 0) {
            throw new BankingAppSystemException("Missing from account id, to account id or amount");
        }
        boolean transactionCreated = transactionsDAO.createTransaction(fromAccId, toAccId, amount);
        if (!transactionCreated) {
            throw new BankingAppSystemException("Could not create transaction");
        }


    }

}
