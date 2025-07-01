package com.example.auth.domain.service;

import com.example.auth.domain.port.out.PasswordHashingPort;
import com.example.auth.domain.port.out.TokenServicePort;
import com.example.auth.application.exception.AuthenticationException;
import com.example.auth.domain.model.AuthToken;
import com.example.auth.domain.model.AuthenticationResult;
import com.example.auth.domain.model.Email;
import com.example.auth.domain.model.User;
import com.example.auth.domain.port.out.UserRepository;

import java.util.Optional;

public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordHashingPort passwordHashingService;
    private final TokenServicePort tokenService;

    public AuthenticationService(UserRepository userRepository,
                                PasswordHashingPort passwordHashingService,
                                TokenServicePort tokenService) {
        this.userRepository = userRepository;
        this.passwordHashingService = passwordHashingService;
        this.tokenService = tokenService;
    }

    public AuthenticationResult authenticate(Email email, String plainPassword) {
        if (email == null || plainPassword == null || plainPassword.isEmpty()) {
            throw new AuthenticationException("Email or password cannot be empty");
        }

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid credentials");
        }

        User user = userOpt.get();
        if (!user.canAuthenticate()) {
            throw new AuthenticationException("User account is not active");
        }

        if (!passwordHashingService.verify(plainPassword, user.getHashedPassword().toString())) {
            throw new AuthenticationException("Invalid credentials");
        }

        try {
            user.recordLogin();
            userRepository.save(user);
            AuthToken accessToken = tokenService.generateAccessToken(user.getId());
            AuthToken refreshToken = tokenService.generateRefreshToken(user.getId());
            return AuthenticationResult.success(user, accessToken, refreshToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AuthenticationException("Authentication failed due to server error");
        }
    }
}