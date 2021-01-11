package com.github.snowhite93.bankingapp.dao.impl;

import com.github.snowhite93.bankingapp.dao.TransactionsDAO;
import com.github.snowhite93.bankingapp.dbutil.PostgresSqlConnection;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.Transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsDAOImpl implements TransactionsDAO {

    private static Transactions extractTransactions(ResultSet rs) throws SQLException {

        Transactions transactions = new Transactions();
        transactions.setAmount(rs.getDouble("amount"));
        transactions.setStatus(rs.getString("status"));
        transactions.setTransactionId(rs.getInt("transaction_id"));
        transactions.setFromAccountId(rs.getInt("from_account_id"));
        transactions.setToAccountId(rs.getInt("to_account_id"));
        transactions.setTransferDate(rs.getDate("transfer_date"));
        return transactions;
    }

    @Override
    public List<Transactions> allTransactionsWithAccId(int accountId) {
        List<Transactions> transactionsWithAccIdList = new ArrayList<>();
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select * from transactions where from_account_id =? OR to_account_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                transactionsWithAccIdList.add(extractTransactions(rs));
            }
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failes to retrieve transactions for account id : " + accountId, e);
        }
        return transactionsWithAccIdList;
    }

    @Override
    public List<Transactions> findIncomingPendingTransactions(int accountId) throws BankingAppSystemException {
        List<Transactions> transactionsWithAccIdList = new ArrayList<>();
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select * from transactions where to_account_id = ? and status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, "Pending");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                transactionsWithAccIdList.add(extractTransactions(rs));
            }
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failes to retrieve transactions for account id : " + accountId, e);
        }
        return transactionsWithAccIdList;
    }


    @Override
    public boolean cancelTransaction(int transactionId) {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "UPDATE transactions set status = ? WHERE transaction_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Canceled");
            preparedStatement.setInt(2, transactionId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failed to cancel transaction", e);
        }
    }

    @Override
    public boolean acceptTransaction(int transactionId) {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "UPDATE transactions set status = ? WHERE transaction_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Approved");
            preparedStatement.setInt(2, transactionId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failed to approve transaction", e);
        }
    }

    @Override
    public Transactions findTransactionById(int transactionId) throws BankingAppSystemException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select * from transactions where transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transactionId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return extractTransactions(rs);
            }
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failed to retrieve transactions with id : " + transactionId, e);
        }
        return null;
    }

    @Override
    public boolean createTransaction(int fromAccId, int toAccId, double amount) throws BankingAppSystemException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "INSERT INTO transactions(from_account_id, to_account_id, amount, status, transfer_date) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, fromAccId);
            preparedStatement.setInt(2, toAccId);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setString(4, "Pending");
            preparedStatement.setDate(5, new java.sql.Date(new Date().getTime()));
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 1;

        } catch (SQLException e) {
            throw new BankingAppSystemException("Failed to create transaction, contact support", e);

        }
    }

    @Override
    public List<Transactions> allTransactions() throws BankingAppSystemException {
        List<Transactions> allTransactions = new ArrayList<>();
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select * from transactions";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                allTransactions.add(extractTransactions(rs));
            }
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failes to retrieve all transactions ");
        }
        return allTransactions;
    }

}
