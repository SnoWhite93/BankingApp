package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.UserDAO;
import com.github.snowhite93.bankingapp.dao.impl.UserDAOImpl;
import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public User findUserByUserNameAndPassword(String userName, String password) throws BankingAppException {
        User user = null;

        if (userName == null || password == null) {
            throw new BankingAppUserException("Missing username/password");
        }
        user = userDAO.findUserByUserNameAndPassword(userName, password);
        if (user == null) {
            throw new BankingAppUserException("No user for this username/password combination.");
        }
        return user;
    }

    @Override
    public User findUserByUserName(String userName) throws BankingAppException {

        if (userName == null) {
            throw new BankingAppUserException("Missing username");
        }
        User user = userDAO.findUserByUserName(userName);
        return user;
    }

    @Override
    public User findUserByUserId(int userId) throws BankingAppException {
        if (userId == 0) {
            throw new BankingAppUserException("Missing user id");
        }
        User user = userDAO.findUserbyUserId(userId);
        return user;
    }

    @Override
    public void createUser(User user) throws BankingAppException {
        checkValidUser(user);
        boolean userCreated = userDAO.createUser(user);
        if (!userCreated) {
            throw new BankingAppSystemException("Could not create user");
        }
    }

    @Override
    public List<User> getAllUsersList() throws BankingAppSystemException {
        List<User> allUsersList = null;
        allUsersList = userDAO.getAllUsers();
        return allUsersList;
    }


    private void checkValidUser(User user) {
        if (user == null) {
            throw new BankingAppUserException("Missing user");
        } else if (user.getPassword() == null) {
            throw new BankingAppUserException("Missing password");
        } else if (user.getUserName() == null) {
            throw new BankingAppUserException("Missing username");
        } else if (user.getDob() == null) {
            throw new BankingAppUserException("Missing date of birth");
        } else if (user.getEmploymentStatus() == null) {
            throw new BankingAppUserException("Missing employment status");
        } else if (user.getFirstName() == null) {
            throw new BankingAppUserException("Missing first name");
        } else if (user.getLastName() == null) {
            throw new BankingAppUserException("Missing last name");
        }
    }

}
