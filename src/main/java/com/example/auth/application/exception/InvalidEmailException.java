package com.example.auth.application.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) { super(message); }
}