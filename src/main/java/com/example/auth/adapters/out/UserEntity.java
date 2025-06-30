package com.example.auth.adapters.out;

import com.example.auth.domain.user.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
                .id(user.getId().getValue())
                .email(user.getEmail().getValue())
                .hashedPassword(user.getHashedPassword().getValue())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    public User toDomain() {
        return User.create(
                new UserId(this.id),
                new Email(this.email),
                new HashedPassword(this.hashedPassword),
                this.status,
                this.createdAt
        );
    }
}
