package com.example.auth.domain.port.out;

import com.example.auth.domain.model.Email;
import com.example.auth.domain.model.User;
import com.example.auth.domain.model.UserId;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(Email email);
    boolean existsByEmail(Email email);
}