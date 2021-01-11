package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.AccountDAO;
import com.github.snowhite93.bankingapp.dao.impl.AccountDAOImpl;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.Account;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public void createAccount(int userId, double startingBalance) throws BankingAppSystemException {
        if (userId == 0) {
            throw new BankingAppSystemException("Could not create account for user id " + userId);
        }
        boolean accountCreated = accountDAO.createAccount(userId, startingBalance);
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

    @Override
    public void deposit(int userId, int accountId, double balanceToDeposit) throws BankingAppSystemException {
        Account account = accountDAO.findAccountByAccId(accountId);
        if (account == null) {
            throw new BankingAppUserException("No such account: " + accountId);
        } else if (account.getUserId() != userId) {
            throw new BankingAppUserException("Cannot deposit into someone else's account, use transaction instead.");
        }

        double newBalance = account.getBalance() + balanceToDeposit;
        updateBalance(accountId, newBalance);
    }

    @Override
    public void withdraw(int userId, int accountId, double balanceToWithdraw) throws BankingAppSystemException {
        Account account = accountDAO.findAccountByAccId(accountId);
        if (account == null) {
            throw new BankingAppUserException("No such account: " + accountId);
        } else if (account.getUserId() != userId) {
            throw new BankingAppUserException("Cannot withdraw from someone else's account, use transaction instead.");
        }

        double newBalance = account.getBalance() - balanceToWithdraw;
        if (balanceToWithdraw > account.getBalance()) {
            throw new BankingAppSystemException("Cannot withdraw more than current balance");
        }
        updateBalance(accountId, newBalance);
    }

}
