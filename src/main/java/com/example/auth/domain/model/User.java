package com.example.auth.domain.model;

import com.example.auth.application.exception.UserNotActiveException;
import lombok.Data;
import lombok.NonNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
public class User {
    @NonNull
    private final UserId id;
    @NonNull
    private final String firstname;
    @NonNull
    private final String lastname;
    private final Email email;
    @NonNull
    private final HashedPassword hashedPassword;
    private UserStatus status;
    @NonNull

    private LocalDate birthDate;
    private BigDecimal balance;

    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public User(@NonNull UserId id, @NonNull String firstname, @NonNull String lastname, @NonNull Email email, @NonNull HashedPassword hashedPassword,
                @NonNull UserStatus status, @NonNull LocalDate birthDate, @NonNull LocalDateTime createdAt) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.status = status;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.lastLoginAt = null;
        this.balance = BigDecimal.ZERO;
    }

    public static User create(@NonNull UserId id, @NonNull String firstname, @NonNull String lastname,
                              @NonNull Email email, @NonNull HashedPassword hashedPassword,
                              @NonNull UserStatus status, @NonNull LocalDate birthDate,
                              @NonNull LocalDateTime createdAt, @NonNull BigDecimal balance) {
        User user = new User(id, firstname, lastname, email, hashedPassword, status, birthDate, createdAt);
        user.balance = balance;
        return user;
    }

    public void recordLogin() {
        if (!status.isActive()) {
            throw new UserNotActiveException("Cannot login inactive user");
        }
        this.lastLoginAt = LocalDateTime.now();
    }

    void activate() {
        this.status = UserStatus.ACTIVE;
    }

    void deactivate() {
        this.status = UserStatus.INACTIVE;
    }

    public boolean canAuthenticate() {
        return status.isActive();
    }
}