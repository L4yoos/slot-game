package com.example.auth.domain.port.out;

import com.example.auth.domain.model.AuthToken;
import com.example.auth.domain.model.UserId;

public interface TokenServicePort {
    AuthToken generateAccessToken(UserId userId);
    boolean isAccessTokenValid(AuthToken token);
    UserId extractUserIdFromAccessToken(AuthToken token);
    AuthToken generateRefreshToken(UserId userId);
    boolean isRefreshTokenValid(AuthToken token);
    UserId extractUserIdFromRefreshToken(AuthToken token);
    void invalidateRefreshToken(AuthToken refreshToken);
}