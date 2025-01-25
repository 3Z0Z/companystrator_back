package com.companystrator.exceptions.controller;

import com.companystrator.exceptions.exception.*;
import com.companystrator.exceptions.response.ResponseException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Hidden
@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(CreateUserException.class)
    public ResponseEntity<ResponseException> handleCreateUserException(CreateUserException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(NoCookiesFoundException.class)
    public ResponseEntity<ResponseException> handleNoCookiesFoundException(NoCookiesFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(TokenNotFoundOrExpiredException.class)
    public ResponseEntity<ResponseException> handleTokenNotFoundOrExpiredException(TokenNotFoundOrExpiredException e) {
        return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(CreateCompanyException.class)
    public ResponseEntity<ResponseException> handleCreateCompanyException(CreateCompanyException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ResponseException> handleCompanyNotFoundException(CompanyNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(CreateProductException.class)
    public ResponseEntity<ResponseException> handleCreateProductException(CreateProductException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseException> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(CreateProductCategoryException.class)
    public ResponseEntity<ResponseException> handleCreateProductCategoryException(CreateProductCategoryException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(ProductCategoryNotFoundException.class)
    public ResponseEntity<ResponseException> handleProductCategoryNotFoundException(ProductCategoryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseException> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ResponseException> handleOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ResponseException> handleClientNotFoundException(ClientNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(JWTTokenNotProvidedException.class)
    public ResponseEntity<ResponseException> handleJWTTokenNotProvidedException(JWTTokenNotProvidedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseException> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
