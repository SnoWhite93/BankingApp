package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.AccountDAO;
import com.github.snowhite93.bankingapp.dao.impl.AccountDAOImpl;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Account;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public void createAccount(int userId) throws BankingAppSystemException {
        if (userId == 0) {
            throw new BankingAppSystemException("Could not create account for user id " + userId);
        }
        boolean accountCreated = accountDAO.createAccount(userId);
        if (!accountCreated) {
            throw new BankingAppSystemException("Could not create account");
        }
    }

    @Override
    public Account findAccountByAccID(int accountId) throws BankingAppSystemException {
        if (accountId == 0) {
            throw new BankingAppSystemException("Missing account for id " + accountId);
        }
        Account account = accountDAO.findAccountByAccId(accountId);
        return account;
    }

    @Override
    public List<Account> retrieveAllAccountsForUserId(int userId) throws BankingAppSystemException {
        List<Account> allAccountsForUserIdList = null;
        if (userId == 0) {
            throw new BankingAppSystemException("Missing user id : " + userId);
        }
        allAccountsForUserIdList = accountDAO.getAllAccountsForUserId(userId);
        return allAccountsForUserIdList;
    }

    @Override
    public void updateBalance(int accountId, double newBalance) throws BankingAppSystemException {
        if (newBalance == 0) {
            throw new BankingAppSystemException("Could not update balance, since new balance is 0");
        }

        boolean updateBalance = accountDAO.updateBalance(accountId, newBalance);
        if (!updateBalance) {
            throw new BankingAppSystemException("Could not update balance");
        }
    }

}