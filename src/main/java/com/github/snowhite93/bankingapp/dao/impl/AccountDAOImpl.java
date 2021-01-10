package com.github.snowhite93.bankingapp.dao.impl;

import com.github.snowhite93.bankingapp.dao.AccountDAO;
import com.github.snowhite93.bankingapp.dbutil.PostgresSqlConnection;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private static Account extractAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        account.setDateCreated(rs.getDate("date_created"));
        return account;
    }

    @Override
    public boolean createAccount(int userId, double startingBalance) throws BankingAppSystemException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "INSERT INTO account(user_id, balance, date_created) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setDouble(2, startingBalance);
            preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new BankingAppSystemException("Couldn't create account", e);
        }
    }

    @Override
    public Account findAccountByAccId(int accountId) throws BankingAppSystemException {
        Account account = null;
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "SELECT account_id, user_id, balance, date_created from account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                account = extractAccount(rs);
            }
        } catch (SQLException e) {
            throw new BankingAppSystemException("Couldn't find account with account id + " + accountId, e);
        }
        return account;
    }

    @Override
    public List<Account> getAllAccountsForUserId(int userId) throws BankingAppSystemException {
        List<Account> accountsForUserIdList = new ArrayList<>();
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select * from account where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                accountsForUserIdList.add(extractAccount(rs));
            }
        } catch (SQLException e) {
            throw new BankingAppSystemException("Couldn't get all accounts for user with user id " + userId, e);
        }
        return accountsForUserIdList;
    }

    @Override
    public boolean updateBalance(int accountId, double newBalance) throws BankingAppSystemException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "UPDATE account set balance = ? where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, accountId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new BankingAppSystemException("Couldn't update balance", e);
        }
    }

}
