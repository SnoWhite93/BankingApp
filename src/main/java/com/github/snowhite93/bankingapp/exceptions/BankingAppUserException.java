package com.github.snowhite93.bankingapp.exceptions;

public class BankingAppUserException extends BankingAppException {

    public BankingAppUserException(String message) {
        super(message);
    }

    public BankingAppUserException(String message, Throwable cause) {
        super(message, cause);
    }

}
