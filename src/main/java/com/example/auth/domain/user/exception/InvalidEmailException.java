package com.example.auth.domain.user.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) { super(message); }
}