package com.example.auth.application.service;

import com.example.auth.application.dto.*;
import com.example.auth.application.exception.AuthenticationException;
import com.example.auth.application.exception.TokenException;
import com.example.auth.application.port.in.AuthCasePort;
import com.example.auth.domain.model.AuthToken;
import com.example.auth.domain.model.AuthenticationResult;
import com.example.auth.domain.service.AuthenticationService;
import com.example.auth.domain.port.out.TokenServicePort;
import com.example.auth.domain.model.Email;
import com.example.auth.domain.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthCase implements AuthCasePort {
    private final AuthenticationService authenticationService;
    private final TokenServicePort tokenService;

    @Override
    public LoginResponse executeLogin(LoginRequest request) {
        Email email;
        try {
            email = Email.from(request.email());
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("Invalid email format");
        }

        AuthenticationResult result = authenticationService.authenticate(email, request.password());

        return new LoginResponse(
                result.getUser().getId(),
                result.getUser().getEmail(),
                result.getAccessToken().toString(),
                result.getRefreshToken().toString()
        );
    }

    @Override
    public RefreshTokenResponse executeRefreshToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new TokenException("Refresh token is missing");
        }

        AuthToken authToken;

        try {
            authToken = new AuthToken(refreshToken);
        } catch (IllegalArgumentException e) {
            throw new TokenException("Malformed refresh token");
        }

        if (!tokenService.isRefreshTokenValid(authToken)) {
            throw new TokenException("Refresh token is invalid or expired");
        }

        try {
            UserId userId = tokenService.extractUserIdFromRefreshToken(authToken);
            AuthToken newAccessToken = tokenService.generateAccessToken(userId);
            AuthToken newRefreshToken = tokenService.generateRefreshToken(userId);

            return new RefreshTokenResponse(newAccessToken.toString(), newRefreshToken.toString());
        } catch (Exception e) {
            throw new TokenException("Token refresh failed due to internal error");
        }
    }

    @Override
    public CheckSessionResponse executeCheckSession(CheckSessionRequest request) {
        String accessToken = request.accessToken();

        if (accessToken == null || accessToken.isBlank()) {
            throw new TokenException("Access token is missing");
        }

        AuthToken authToken;
        try {
            authToken = new AuthToken(accessToken);
        } catch (IllegalArgumentException e) {
            throw new TokenException("Malformed access token");
        }

        if (!tokenService.isAccessTokenValid(authToken)) {
            throw new TokenException("Access token is invalid or expired");
        }

        try {
            UserId userId = tokenService.extractUserIdFromAccessToken(authToken);
            return new CheckSessionResponse(userId.toString());
        } catch (Exception e) {
            throw new TokenException("Failed to validate session due to internal error");
        }
    }

    @Override
    public void executeLogout(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new TokenException("Refresh token is missing");
        }

        try {
            tokenService.invalidateRefreshToken(new AuthToken(refreshToken));
        } catch (IllegalArgumentException e) {
            throw new TokenException("Malformed refresh token");
        } catch (Exception e) {
            throw new TokenException("Failed to logout due to internal error");
        }
    }
}