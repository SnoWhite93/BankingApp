package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Account;

import java.util.List;

public interface AccountService {

    public void createAccount(int userId, double startingBalance) throws BankingAppSystemException;

    public Account findAccountByAccID(int accountId) throws BankingAppException;

    public List<Account> retrieveAllAccountsForUserId(int userId) throws BankingAppSystemException;

    public void updateBalance(int accountId, double newBalance) throws BankingAppException;

    public void deposit(int userId, int accountId, double balanceToDeposit) throws BankingAppException;

    public void withdraw(int userId, int accountId, double balanceToWithdraw) throws BankingAppException;

}
