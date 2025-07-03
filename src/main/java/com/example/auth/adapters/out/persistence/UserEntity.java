package com.example.auth.adapters.out.persistence;

import com.example.auth.domain.model.*;
import com.example.slot.adapters.out.persistence.SpinResultEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false)
    private LocalDate birthDate;

    private BigDecimal balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SpinResultEntity> spinHistory;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
                .id(user.getId().getValue())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail().getValue())
                .hashedPassword(user.getHashedPassword().getValue())
                .status(user.getStatus())
                .birthDate(user.getBirthDate())
                .balance(user.getBalance())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    public User toDomain() {
        return User.create(
                new UserId(this.id),
                this.firstname,
                this.lastname,
                new Email(this.email),
                new HashedPassword(this.hashedPassword),
                this.status,
                this.birthDate,
                this.createdAt,
                this.balance != null ? this.balance : BigDecimal.ZERO
        );
    }
}
