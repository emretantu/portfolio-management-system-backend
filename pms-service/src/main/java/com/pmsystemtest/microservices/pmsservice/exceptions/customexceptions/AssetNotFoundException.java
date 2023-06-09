package com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions;

public class AssetNotFoundException extends RuntimeException{

    public AssetNotFoundException() {
    }

    public AssetNotFoundException(String message) {
        super(message);
    }

    public AssetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetNotFoundException(Throwable cause) {
        super(cause);
    }
}
