package com.example.auth.application.exception;

public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException(String message) { super(message); }
}