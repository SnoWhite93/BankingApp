package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.UserDAO;
import com.github.snowhite93.bankingapp.dao.impl.UserDAOImpl;
import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.User;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public User findUserByUserNameAndPassword(String userName, String password) throws BankingAppException {
        User user = null;

        if (userName == null || password == null) {
            throw new BankingAppUserException("Missing username/password");
        }
        user = userDAO.findUserByUserNameAndPassword(userName, password);
        return user;
    }

    @Override
    public User findUserByUserName(String userName) throws BankingAppException {
        User user = null;
        if (userName == null) {
            throw new BankingAppUserException("Missing username");
        }
        user = userDAO.findUserByUserName(userName);
        return user;
    }

    @Override
    public void createUser(User user) throws BankingAppException {
        if (user == null) {
            throw new BankingAppUserException("Missing user");
        }
        userDAO.createUser(user);

    }

}
