package com.example.auth.adapters.out;

import com.example.auth.domain.port.out.PasswordHashingPort;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashingAdapter implements PasswordHashingPort {
    private final PasswordEncoder passwordEncoder;

    public PasswordHashingAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean verify(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}