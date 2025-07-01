package com.example.auth.application.dto;

import java.time.LocalDate;

public record RegisterRequest(String firstname, String lastname, String email, String password, LocalDate birthDate) {}