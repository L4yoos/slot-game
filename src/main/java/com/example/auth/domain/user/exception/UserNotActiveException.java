package com.example.auth.domain.user.exception;

public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException(String message) { super(message); }
}