package com.example.auth.adapters.in.rest;

import com.example.auth.application.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserCase userCase;

    public AuthController(UserCase userCase) {
        this.userCase = userCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userCase.execute(request));
    }

    //TODO Login, refresh-token, check-session
}