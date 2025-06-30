package com.example.auth.config;

import com.example.auth.application.user.UserCase;
import com.example.auth.domain.user.UserFactory;
import com.example.auth.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfiguration {

    private final UserRepository userRepository;

    public AuthConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserFactory userFactory(PasswordEncoder passwordEncoder) {
        return new UserFactory(passwordEncoder);
    }

    @Bean
    public UserCase userCase(UserFactory userFactory) {
        return new UserCase(userRepository, userFactory);
    }
}