package com.example.messageservice.domain.exception;

public class TokenUnauthorizedException extends RuntimeException {
    public TokenUnauthorizedException(String message) {
        super(message);
    }
}
