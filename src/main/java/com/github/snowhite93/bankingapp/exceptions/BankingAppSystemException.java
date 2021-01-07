package com.github.snowhite93.bankingapp.exceptions;

public class BankingAppSystemException extends BankingAppException {

    public BankingAppSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankingAppSystemException(String message) {
        super(message);
    }

}
