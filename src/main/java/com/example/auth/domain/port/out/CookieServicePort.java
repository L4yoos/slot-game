package com.example.auth.domain.port.out;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieServicePort {
    void setAuthCookies(HttpServletResponse response, String accessToken, String refreshToken);
    String getRefreshToken(HttpServletRequest request);
    String getAccessToken(HttpServletRequest request);
    void clearAuthCookies(HttpServletResponse response);
}