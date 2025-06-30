package com.example.auth.domain.user;

public enum UserStatus {
    ACTIVE, INACTIVE, SUSPENDED;

    public boolean isActive() {
        return this == ACTIVE;
    }
}