package com.example.auth.application.port.in;

import com.example.auth.application.dto.RegisterRequest;
import com.example.auth.application.dto.RegisterResponse;

public interface UserCasePort {
    RegisterResponse execute(RegisterRequest request);
}
