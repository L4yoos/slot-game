package com.example.auth.config;

import com.example.auth.adapters.out.persistence.JpaRefreshTokenRepositoryAdapter;
import com.example.auth.adapters.out.security.JwtTokenServiceAdapter;
import com.example.auth.adapters.out.security.PasswordHashingAdapter;
import com.example.auth.application.service.AuthCase;
import com.example.auth.application.service.UserCase;
import com.example.auth.domain.port.out.*;
import com.example.auth.domain.service.AuthenticationService;
import com.example.auth.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfiguration {

    private final UserRepository userRepository;
    private final JpaRefreshTokenPort jpaRefreshTokenPort;

    public AuthConfiguration(UserRepository userRepository, JpaRefreshTokenPort jpaRefreshTokenPort) {
        this.userRepository = userRepository;
        this.jpaRefreshTokenPort = jpaRefreshTokenPort;
    }

    @Bean
    public UserCase userCase(UserService userService) {
        return new UserCase(userRepository, userService);
    }

    @Bean
    public PasswordHashingPort passwordHashingPort(PasswordEncoder passwordEncoder) {
        return new PasswordHashingAdapter(passwordEncoder);
    }

    @Bean
    public UserService userFactory(PasswordHashingPort passwordHashingPort) {
        return new UserService(passwordHashingPort);
    }

    @Bean
    public RefreshTokenRepositoryPort refreshTokenRepositoryPort() {
        return new JpaRefreshTokenRepositoryAdapter(jpaRefreshTokenPort);
    }

    @Bean
    public TokenServicePort tokenServicePort(RefreshTokenRepositoryPort refreshTokenRepositoryPort) {
        return new JwtTokenServiceAdapter(refreshTokenRepositoryPort);
    }

    @Bean
    public AuthenticationService authenticationService(UserRepository userRepository,
                                                       PasswordHashingPort passwordHashingPort,
                                                       TokenServicePort tokenServicePort) {
        return new AuthenticationService(userRepository, passwordHashingPort, tokenServicePort);
    }

    @Bean
    public AuthCase authCase(AuthenticationService authenticationService,
                                 TokenServicePort tokenServicePort) {
        return new AuthCase(authenticationService, tokenServicePort);
    }
}