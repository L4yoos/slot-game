package com.example.auth.application.user;

import com.example.auth.domain.user.*;
import com.example.auth.domain.user.exception.UserAlreadyExistsException;

public class UserCase {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public UserCase(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    public RegisterResponse execute(RegisterRequest request) {
        Email email = Email.from(request.getEmail());
        
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        User user = userFactory.createUser(email, request.getPassword());
        userRepository.save(user);

        return new RegisterResponse(user.getId(), user.getEmail(), user.getStatus());
    }
}