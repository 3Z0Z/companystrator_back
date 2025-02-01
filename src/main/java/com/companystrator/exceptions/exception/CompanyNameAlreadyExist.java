package com.companystrator.exceptions.exception;

public class CompanyNameAlreadyExist extends RuntimeException {
    public CompanyNameAlreadyExist(String message) {
        super(message);
    }
}
