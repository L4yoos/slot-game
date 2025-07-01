package com.example.auth.domain.model;

import lombok.Value;

@Value
public class AuthToken {
    private final String value;

    public AuthToken(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}