package com.github.snowhite93.bankingapp.service;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.model.AccountRequest;

import java.util.List;

public interface AccountRequestService {

    public List<AccountRequest> findUserRequests(int userId);

    public List<AccountRequest> findAllPendingAccReqs() throws BankingAppException;

    public void createRequest(int userId) throws BankingAppException;

    public void rejectAccRequest(int requestId, String rejectionReaction) throws BankingAppException;

    public void acceptAccRequest(int requestId) throws BankingAppException;

}
