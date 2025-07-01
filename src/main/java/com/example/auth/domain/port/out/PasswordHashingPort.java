package com.example.auth.domain.port.out;

public interface PasswordHashingPort {
    String hash(String password);
    boolean verify(String plainPassword, String hashedPassword);
}