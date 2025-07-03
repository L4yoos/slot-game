package com.example.auth.adapters.out.persistence;

import com.example.auth.domain.port.out.JpaRefreshTokenPort;
import com.example.auth.domain.model.AuthToken;
import com.example.auth.domain.model.UserId;
import com.example.auth.domain.port.out.RefreshTokenRepositoryPort;

import java.util.Optional;

public class JpaRefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {
    private final JpaRefreshTokenPort jpaRepository;

    public JpaRefreshTokenRepositoryAdapter(JpaRefreshTokenPort jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(AuthToken refreshToken, UserId userId, long expirationMs) {
        RefreshTokenEntity entity = new RefreshTokenEntity(
            refreshToken.getValue(),
            userId.toString(),
            System.currentTimeMillis() + expirationMs
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<AuthToken> findByToken(String token) {
        return jpaRepository.findById(token)
                .filter(entity -> entity.getExpirationTime() > System.currentTimeMillis())
                .map(entity -> new AuthToken(entity.getToken()));
    }

    @Override
    public void deleteByToken(String token) {
        jpaRepository.deleteById(token);
    }
}