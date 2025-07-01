package com.example.auth.adapters.out;

import com.example.auth.domain.model.AuthToken;
import com.example.auth.domain.model.UserId;
import com.example.auth.domain.port.out.RefreshTokenRepositoryPort;
import com.example.auth.domain.port.out.TokenServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class JwtTokenServiceAdapter implements TokenServicePort {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-ms}")
    private Long ACCESS_TOKEN_EXPIRATION_MS;

    @Value("${jwt.refresh-token-expiration-ms}")
    private Long REFRESH_TOKEN_EXPIRATION_MS;

    private static final String ISSUER = "auth-service";
    private final RefreshTokenRepositoryPort refreshTokenRepository;

    public JwtTokenServiceAdapter(RefreshTokenRepositoryPort refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public AuthToken generateAccessToken(UserId userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        String token = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return new AuthToken(token);
    }

    @Override
    public boolean isAccessTokenValid(AuthToken token) {
        if (token == null) {
            return false;
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token.getValue());
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public UserId extractUserIdFromAccessToken(AuthToken token) {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token.getValue())
                    .getBody();
            if (!ISSUER.equals(claims.getIssuer())) {
                throw new IllegalArgumentException("Invalid issuer");
            }
            return new UserId(UUID.fromString(claims.getSubject()));
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

    @Override
    public AuthToken generateRefreshToken(UserId userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        String token = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        AuthToken refreshToken = new AuthToken(token);
        refreshTokenRepository.save(refreshToken, userId, REFRESH_TOKEN_EXPIRATION_MS);
        return refreshToken;
    }

    @Override
    public boolean isRefreshTokenValid(AuthToken token) {
        if (token == null) {
            return false;
        }
        Optional<AuthToken> storedToken = refreshTokenRepository.findByToken(token.getValue());
        if (storedToken.isEmpty()) {
            return false;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token.getValue())
                    .getBody();
            return ISSUER.equals(claims.getIssuer()) && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public UserId extractUserIdFromRefreshToken(AuthToken token) {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        Optional<AuthToken> storedToken = refreshTokenRepository.findByToken(token.getValue());
        if (storedToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token not found");
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token.getValue())
                    .getBody();
            if (!ISSUER.equals(claims.getIssuer())) {
                throw new IllegalArgumentException("Invalid issuer");
            }
            return new UserId(UUID.fromString(claims.getSubject()));
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid refresh token", e);
        }
    }

    @Override
    public void invalidateRefreshToken(AuthToken refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken.getValue());
    }
}