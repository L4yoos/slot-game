package com.example.auth.application.exception;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}