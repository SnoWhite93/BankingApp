package com.github.snowhite93.bankingapp.dao;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.model.User;

public interface UserDAO {

    public User findUserByUserNameAndPassword(String userName, String password) throws BankingAppException;

    public User findUserByUserName(String userName) throws BankingAppException;

    public void createUser(User user) throws BankingAppException;

}
