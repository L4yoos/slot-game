package com.example.auth.domain.model;

import lombok.Value;

@Value
public class AuthenticationResult {
    private final boolean success;
    private final User user;
    private final AuthToken accessToken;
    private final AuthToken refreshToken;
    private final String errorMessage;

    private AuthenticationResult(boolean success, User user, AuthToken accessToken, AuthToken refreshToken, String errorMessage) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.errorMessage = errorMessage;
    }

    public static AuthenticationResult success(User user, AuthToken accessToken, AuthToken refreshToken) {
        return new AuthenticationResult(true, user, accessToken, refreshToken, null);
    }

    public static AuthenticationResult failed(String errorMessage) {
        return new AuthenticationResult(false, null, null, null, errorMessage);
    }
}