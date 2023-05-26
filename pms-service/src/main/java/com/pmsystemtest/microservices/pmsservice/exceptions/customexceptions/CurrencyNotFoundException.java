package com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions;

public class CurrencyNotFoundException extends RuntimeException{

    public CurrencyNotFoundException() {
    }

    public CurrencyNotFoundException(String message) {
        super(message);
    }

    public CurrencyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyNotFoundException(Throwable cause) {
        super(cause);
    }
}
