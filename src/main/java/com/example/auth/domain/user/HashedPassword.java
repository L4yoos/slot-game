package com.example.auth.domain.user;

import lombok.NonNull;
import lombok.Value;

@Value
public class HashedPassword {
    String value;

    public HashedPassword(@NonNull String hashedValue) {
        if (hashedValue.trim().isEmpty()) {
            throw new IllegalArgumentException("Hashed password cannot be null or empty");
        }
        this.value = hashedValue;
    }
}