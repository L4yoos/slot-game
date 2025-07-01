package com.example.auth.application.dto;

import com.example.auth.domain.model.Email;
import com.example.auth.domain.model.UserId;
import com.example.auth.domain.model.UserStatus;
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