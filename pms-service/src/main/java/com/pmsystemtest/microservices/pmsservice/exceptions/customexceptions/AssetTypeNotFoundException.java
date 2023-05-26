package com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions;

public class AssetTypeNotFoundException extends RuntimeException{

    public AssetTypeNotFoundException() {
    }

    public AssetTypeNotFoundException(String message) {
        super(message);
    }

    public AssetTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssetTypeNotFoundException(Throwable cause) {
        super(cause);
    }
}
