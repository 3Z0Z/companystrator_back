package com.companystrator.exceptions.exception;

public class JWTTokenNotProvidedException extends RuntimeException {

    public JWTTokenNotProvidedException(String message) {
        super(message);
    }

}
