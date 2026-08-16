package com.ecom.authservice.exception;

public class JWTException extends RuntimeException{

    public JWTException(String message) {
        super(message);
    }
}
