package com.example.auth.domain.port.out;

import com.example.auth.domain.model.AuthToken;
import com.example.auth.domain.model.UserId;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {
    void save(AuthToken refreshToken, UserId userId, long expirationMs);
    Optional<AuthToken> findByToken(String token);
    void deleteByToken(String token);
}