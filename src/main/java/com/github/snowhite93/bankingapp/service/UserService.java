package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.User;

public interface UserService {

    public User findUserByUserNameAndPassword(String userName, String password) throws BankingAppException;

    public User findUserByUserName(String userName) throws BankingAppException;

    public void createUser(User user) throws BankingAppException;

}
