package com.github.snowhite93.bankingapp.dbutil;

import com.github.snowhite93.bankingapp.exceptions.BankingAppSystemException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class PostgresSqlConnection {

    private static final Logger log = Logger.getLogger(PostgresSqlConnection.class);

    private static Connection connection;

    private PostgresSqlConnection() {

    }

    public static Connection getConnection() throws BankingAppSystemException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.fatal("Driver class missing", e);
            throw new BankingAppSystemException("System failed, contact the admin", e);
        }
        String url = "jdbc:postgresql://localhost:5433/postgres?currentSchema=\"bankApp\"";
        String username = "postgres";
        String password = "postgres";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            log.error("Failed to connect to database", e);
            throw new BankingAppSystemException("System failed, contact the admin", e);
        }
        return connection;

    }

}