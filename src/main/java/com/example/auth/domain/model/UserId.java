package com.example.auth.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class UserId {
    UUID value;

    public UserId(@NonNull UUID value) {
        this.value = value;
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId from(String value) {
        return new UserId(UUID.fromString(value));
    }

    public static UserId from(@NonNull UUID value) {
        return new UserId(value);
    }

    @JsonValue
    public UUID getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}