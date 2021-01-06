package com.github.snowhite93.bankingapp.dao;

import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.model.AccountRequest;

import java.util.List;

public interface AccountRequestDAO {

    public List<AccountRequest> findUserRequests(int userId);

    public List<AccountRequest> findAllPendingAccReqs() throws BankingAppException;

    public boolean createRequest(int userId) throws BankingAppException;

    public boolean rejectAccRequest(int requestId, String rejectionReaction) throws BankingAppException;

    public boolean acceptAccRequest(int requestId) throws BankingAppException;

}
