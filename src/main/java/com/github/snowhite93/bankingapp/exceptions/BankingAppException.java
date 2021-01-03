package com.github.snowhite93.bankingapp.exceptions;

public class BankingAppException extends RuntimeException{

    public BankingAppException(String message) {
        super(message);
    }

    public BankingAppException(String message, Throwable cause) {
        super(message, cause);
    }

}
