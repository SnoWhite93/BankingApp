package com.github.snowhite93.bankingapp.dao;

import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Account;

import java.util.List;

public interface AccountDAO {

    public boolean createAccount(int userId, double startingBalance) throws BankingAppSystemException;
    public Account findAccountByAccId(int accountId)throws BankingAppSystemException;
    public List<Account> getAllAccountsForUserId(int userId) throws BankingAppSystemException;
    public boolean updateBalance(int accountId, double newBalance) throws BankingAppSystemException;

}
