package com.example.auth.application.port.in;

import com.example.auth.application.dto.*;

public interface AuthCasePort {
    LoginResponse executeLogin(LoginRequest request);
    RefreshTokenResponse executeRefreshToken(RefreshTokenRequest request);
    CheckSessionResponse executeCheckSession(CheckSessionRequest request);
    void executeLogout(RefreshTokenRequest request);
}
