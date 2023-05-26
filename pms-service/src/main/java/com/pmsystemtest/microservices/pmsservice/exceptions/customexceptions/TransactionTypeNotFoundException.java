package com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions;

public class TransactionTypeNotFoundException extends RuntimeException{

    public TransactionTypeNotFoundException() {
    }

    public TransactionTypeNotFoundException(String message) {
        super(message);
    }

    public TransactionTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionTypeNotFoundException(Throwable cause) {
        super(cause);
    }
}
