package com.example.auth.domain.user;

import com.example.auth.domain.user.exception.UserNotActiveException;
import lombok.Data;
import lombok.NonNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class User {
    @NonNull
    private final UserId id;
    @NonNull
    private final Email email;
    @NonNull
    private final HashedPassword hashedPassword;
    private UserStatus status;
    @NonNull
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    User(@NonNull UserId id, @NonNull Email email, @NonNull HashedPassword hashedPassword,
                 @NonNull UserStatus status, @NonNull LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.status = status;
        this.createdAt = createdAt;
        this.lastLoginAt = null;
    }

    public static User create(@NonNull UserId id, @NonNull Email email, @NonNull HashedPassword hashedPassword,
                              @NonNull UserStatus status, @NonNull LocalDateTime createdAt) {
        return new User(id, email, hashedPassword, status, createdAt);
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