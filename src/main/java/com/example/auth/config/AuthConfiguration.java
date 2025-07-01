package com.example.auth.config;

import com.example.auth.adapters.out.*;
import com.example.auth.application.service.AuthCase;
import com.example.auth.application.service.UserCase;
import com.example.auth.domain.port.out.UserRepository;
import com.example.auth.domain.service.AuthenticationService;
import com.example.auth.domain.port.out.PasswordHashingPort;
import com.example.auth.domain.port.out.RefreshTokenRepositoryPort;
import com.example.auth.domain.port.out.TokenServicePort;
import com.example.auth.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfiguration {

    private final UserRepository userRepository;
    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;

    public AuthConfiguration(UserRepository userRepository, JpaRefreshTokenRepository jpaRefreshTokenRepository) {
        this.userRepository = userRepository;
        this.jpaRefreshTokenRepository = jpaRefreshTokenRepository;
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
        return new JpaRefreshTokenRepositoryAdapter(jpaRefreshTokenRepository);
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