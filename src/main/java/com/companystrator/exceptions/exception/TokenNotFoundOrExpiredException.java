package com.companystrator.exceptions.exception;

public class TokenNotFoundOrExpiredException extends RuntimeException {

    public TokenNotFoundOrExpiredException(String message) {
        super(message);
    }

}
