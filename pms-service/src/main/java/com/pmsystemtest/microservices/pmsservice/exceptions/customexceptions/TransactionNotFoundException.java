package com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException() {
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }

    public TransactionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionNotFoundException(Throwable cause) {
        super(cause);
    }
}
