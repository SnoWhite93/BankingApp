package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.dao.AccountRequestDAO;
import com.github.snowhite93.bankingapp.dao.impl.AccountRequestDAOImpl;
import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppUserException;
import com.github.snowhite93.bankingapp.model.AccountRequest;

import java.util.List;

public class AccountRequestServiceImpl implements AccountRequestService {

    private AccountRequestDAO accountRequestDAO = new AccountRequestDAOImpl();

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
    public void createRequest(int userId) throws BankingAppException {
        if (userId == 0) {
            throw new BankingAppException("Could not create request for user id " + userId);
        }
        boolean requestCreated = accountRequestDAO.createRequest(userId);
        if (!requestCreated) {
            throw new BankingAppException("Could not create request");
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
    }

}


