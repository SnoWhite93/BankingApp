package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.AccountDAO;
import com.github.snowhite93.bankingapp.dao.AccountRequestDAO;
import com.github.snowhite93.bankingapp.dao.impl.AccountDAOImpl;
import com.github.snowhite93.bankingapp.dao.impl.AccountRequestDAOImpl;
import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.AccountRequest;

import java.util.List;

public class AccountRequestServiceImpl implements AccountRequestService {

    private AccountRequestDAO accountRequestDAO = new AccountRequestDAOImpl();
    private AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public List<AccountRequest> findUserRequests(int userId) {
        List<AccountRequest> userRequestsList = null;
        if (userId == 0) {
            throw new BankingAppUserException("Missing user ID");
        }
        userRequestsList = accountRequestDAO.findUserRequests(userId);
        return userRequestsList;
    }

    @Override
    public List<AccountRequest> findAllPendingAccReqs() throws BankingAppException {
        return accountRequestDAO.findAllPendingAccReqs();
    }

    @Override
    public void createRequest(int userId, double startingBalance) throws BankingAppException {
        if (startingBalance < 25) {
            throw new BankingAppException("Could not create request for user id " + userId + " because starting balance was less than $25.");
        }
        boolean requestCreated = accountRequestDAO.createRequest(userId, startingBalance);
        if (!requestCreated) {
            throw new BankingAppException("Could not create request for user id " + userId);
        }
    }

    @Override
    public void rejectAccRequest(int requestId, String rejectionReason) throws BankingAppException {
        boolean rejectedAccRequest = accountRequestDAO.rejectAccRequest(requestId, rejectionReason);
        if (!rejectedAccRequest) {
            throw new BankingAppException("Could not reject account request with request id " + requestId + " with the rejection reason being " + rejectionReason);

        }
    }

    @Override
    public void acceptAccRequest(int requestId) throws BankingAppException {
        boolean acceptedAccRequest = accountRequestDAO.acceptAccRequest(requestId);
        if (!acceptedAccRequest) {
            throw new BankingAppException("Could not accept account request with request id " + requestId);
        }

        AccountRequest request = accountRequestDAO.findRequestById(requestId);

        boolean accountCreated = accountDAO.createAccount(request.getUserId(), request.getStartingBalance());
        if (!accountCreated) {
            throw new BankingAppSystemException("Could not create account");
        }

    }

}


