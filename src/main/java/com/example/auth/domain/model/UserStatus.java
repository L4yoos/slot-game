package com.example.auth.domain.model;

public enum UserStatus {
    ACTIVE, INACTIVE, SUSPENDED;

    public boolean isActive() {
        return this == ACTIVE;
    }
}