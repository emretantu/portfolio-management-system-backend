package com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions;

public class PortfolioNotFoundException extends RuntimeException{

    public PortfolioNotFoundException() {
    }

    public PortfolioNotFoundException(String message) {
        super(message);
    }

    public PortfolioNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PortfolioNotFoundException(Throwable cause) {
        super(cause);
    }
}
