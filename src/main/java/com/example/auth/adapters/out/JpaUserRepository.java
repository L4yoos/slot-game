package com.example.auth.adapters.out;

import com.example.auth.domain.user.*;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {
    private final SpringUserRepository springRepository;

    public JpaUserRepository(SpringUserRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public void save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        springRepository.save(entity);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return springRepository.findById(id.getValue())
                .map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return springRepository.findByEmail(email.getValue())
                .map(UserEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return springRepository.existsByEmail(email.getValue());
    }
}