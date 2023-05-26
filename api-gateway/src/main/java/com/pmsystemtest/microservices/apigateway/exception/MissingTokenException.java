package com.pmsystemtest.microservices.apigateway.exception;

public class MissingTokenException extends RuntimeException{

    public MissingTokenException() {
    }

    public MissingTokenException(String message) {
        super(message);
    }

    public MissingTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingTokenException(Throwable cause) {
        super(cause);
    }
}
