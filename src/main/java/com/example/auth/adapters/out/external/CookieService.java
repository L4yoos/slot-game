package com.example.auth.adapters.out.external;

import com.example.auth.domain.port.out.CookieServicePort;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService implements CookieServicePort {

    @Override
    public void setAuthCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false); // Set to true in production (HTTPS)
        accessTokenCookie.setPath("/api");
        accessTokenCookie.setMaxAge(3600);
        accessTokenCookie.setAttribute("SameSite", "Strict");

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // Set to true in production (HTTPS)
        refreshTokenCookie.setPath("/api");
        refreshTokenCookie.setMaxAge(604800);
        refreshTokenCookie.setAttribute("SameSite", "Strict");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    @Override
    public String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public String getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void clearAuthCookies(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("accessToken", "");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false); // Set to true in production (HTTPS)
        accessTokenCookie.setPath("/api");
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setAttribute("SameSite", "Strict");

        Cookie refreshTokenCookie = new Cookie("refreshToken", "");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // Set to true in production (HTTPS)
        refreshTokenCookie.setPath("/api");
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setAttribute("SameSite", "Strict");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }
}