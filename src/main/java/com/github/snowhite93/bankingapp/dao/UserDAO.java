package com.github.snowhite93.bankingapp.dao;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.User;

public interface UserDAO {

    public User findUserByUserNameAndPassword(String userName, String password) throws BankingAppSystemException;

    public User findUserByUserName(String userName) throws BankingAppSystemException;

    public boolean createUser(User user) throws BankingAppException;

}
