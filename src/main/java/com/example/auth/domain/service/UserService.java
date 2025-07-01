package com.example.auth.domain.service;

import com.example.auth.domain.port.out.PasswordHashingPort;
import com.example.auth.domain.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserService {
    private final PasswordHashingPort passwordHashingPort;

    public UserService(PasswordHashingPort passwordHashingPort) {
        this.passwordHashingPort = passwordHashingPort;
    }

    public User createUser(String firstname, String lastname, Email email, String plainPassword, LocalDate birthDate) {
        if (email == null || plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Email or password cannot be null or empty");
        }
        HashedPassword hashedPassword = new HashedPassword(passwordHashingPort.hash(plainPassword));
        return new User(UserId.generate(), firstname, lastname, email, hashedPassword, UserStatus.ACTIVE, birthDate, LocalDateTime.now());
    }
}
