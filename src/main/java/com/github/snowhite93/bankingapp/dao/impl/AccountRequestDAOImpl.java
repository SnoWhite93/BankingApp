package com.github.snowhite93.bankingapp.dao.impl;

import com.github.snowhite93.bankingapp.dao.AccountRequestDAO;
import com.github.snowhite93.bankingapp.dbutil.PostgresSqlConnection;
import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.AccountRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRequestDAOImpl implements AccountRequestDAO {

    private static AccountRequest extractAccReq(ResultSet rs) throws SQLException {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setRequestId(rs.getInt("request_id"));
        accountRequest.setUserId(rs.getInt("user_id"));
        accountRequest.setRequestStatus(rs.getString("request_status"));
        accountRequest.setRejectionReason(rs.getString("rejection_reason"));
        return accountRequest;
    }

    @Override
    public List<AccountRequest> findUserRequests(int userId) {
        List<AccountRequest> accountRequestsByUserIdList = new ArrayList<>();
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select * from account_request where user_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                accountRequestsByUserIdList.add(extractAccReq(rs));
            }

        } catch (SQLException e) {
            throw new BankingAppSystemException("Failes to retrieve account request for user with user id " + userId, e);
        }
        return accountRequestsByUserIdList;
    }

    @Override
    public List<AccountRequest> findAllPendingAccReqs() throws BankingAppException {
        List<AccountRequest> pendingRequestsList = new ArrayList<>();
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select * from account_request where request_status =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Pending");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                pendingRequestsList.add(extractAccReq(rs));
            }
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failes to retrieve account request status", e);
        }
        return pendingRequestsList;
    }

    @Override
    public boolean createRequest(int userId) throws BankingAppException {

        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "INSERT INTO account_request(user_id, request_status) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, "Pending");
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 1;

        } catch (SQLException e) {
            throw new BankingAppSystemException("Failed to create request", e);
        }

    }

    @Override
    public boolean rejectAccRequest(int requestId, String rejectionReason) throws BankingAppException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "UPDATE account_request set request_status = ?, rejection_reason = ? WHERE request_id =? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Rejected");
            preparedStatement.setString(2, rejectionReason);
            preparedStatement.setInt(3, requestId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows ==1;
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failed to reject request", e);
        }

    }

    @Override
    public boolean acceptAccRequest(int requestId) throws BankingAppException {
       try ( Connection connection = PostgresSqlConnection.getConnection()) {
        String sql = "UPDATE account_request set request_status = ? WHERE request_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Approved");
            preparedStatement.setInt(2, requestId);
           int affectedRows = preparedStatement.executeUpdate();
           return affectedRows == 1;
       } catch (SQLException e) {
           throw new BankingAppSystemException("Failed to accept request", e);
        }

    }

}
