package com.example.auth.application.user;

import com.example.auth.domain.user.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private UserId userId;
    private Email email;
    private UserStatus status;
}