package com.github.snowhite93.bankingapp.dao.impl;

import com.github.snowhite93.bankingapp.dao.UserDAO;
import com.github.snowhite93.bankingapp.dbutil.PostgresSqlConnection;
import com.github.snowhite93.bankingapp.exceptions.BankingAppException;
import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import com.github.snowhite93.bankingapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    private static User extractUser(ResultSet rs) throws SQLException {

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUserName(rs.getString("user_name"));
//      user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmployee(rs.getBoolean("is_employee"));
        user.setEmploymentStatus(rs.getString("employment_status"));
        user.setDob(rs.getDate("dob"));
        return user;
    }

    @Override
    public User findUserByUserNameAndPassword(String userName, String password) throws BankingAppSystemException {
        User user = null;
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select user_id, user_name, first_name, last_name, is_employee, employment_status, dob from user where user_name=? and password =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failes to read user, contact support", e);
        }
        return user;
    }

    @Override
    public User findUserByUserName(String userName) throws BankingAppSystemException {
        User user = null;
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "select user_id, user_name, first_name, last_name, is_employee, employment_status, dob from user where user_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException e) {
            throw new BankingAppSystemException("Failed to read user, contact support", e);
        }
        return user;
    }

    @Override
    public boolean createUser(User user) throws BankingAppException {
        try (Connection connection = PostgresSqlConnection.getConnection()) {
            String sql = "INSERT INTO user(user_name, password, first_name, last_name, is_employee, employment_status, dob) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setBoolean(5, user.isEmployee());
            preparedStatement.setString(6, user.getEmploymentStatus());
            preparedStatement.setDate(7, new java.sql.Date(user.getDob().getTime()));

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows ==1;

        } catch (SQLException e) {
            throw new BankingAppSystemException("Failed to create user, contact support", e);

        }

    }

}
