package com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions;

public class TransactionNoContentException extends RuntimeException{

    public TransactionNoContentException() {
    }

    public TransactionNoContentException(String message) {
        super(message);
    }

    public TransactionNoContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionNoContentException(Throwable cause) {
        super(cause);
    }
}
