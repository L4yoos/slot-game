package com.example.auth.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    public UserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(Email email, String plainPassword) {
        HashedPassword hashedPassword = new HashedPassword(passwordEncoder.encode(plainPassword));
        return new User(UserId.generate(), email, hashedPassword, UserStatus.ACTIVE, LocalDateTime.now());
    }
}
