package com.example.auth.application.service;

import com.example.auth.application.dto.RegisterRequest;
import com.example.auth.application.dto.RegisterResponse;
import com.example.auth.application.port.in.UserCasePort;
import com.example.auth.domain.model.Email;
import com.example.auth.domain.model.User;
import com.example.auth.domain.port.out.UserRepository;
import com.example.auth.domain.service.UserService;
import com.example.auth.application.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCase implements UserCasePort {
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public RegisterResponse execute(RegisterRequest request) {
        Email email = Email.from(request.email());
        
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        User user = userService.createUser(request.firstname(), request.lastname(), email, request.password(), request.birthDate());
        userRepository.save(user);

        return new RegisterResponse(user.getId(), user.getEmail(), user.getStatus());
    }
}