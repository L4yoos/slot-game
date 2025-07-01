package com.example.auth.domain.model;

import com.example.auth.application.exception.InvalidEmailException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

@Value
public class Email {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    String value;

    public Email(@NonNull String value) {
        if (value.trim().isEmpty()) {
            throw new InvalidEmailException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidEmailException("Invalid email format: " + value);
        }
        this.value = value.toLowerCase().trim();
    }

    public static Email from(@NonNull String value) {
        return new Email(value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}