package com.example.auth.adapters.in.rest;

import com.example.auth.application.dto.*;
import com.example.auth.application.port.in.AuthCasePort;
import com.example.auth.application.port.in.UserCasePort;
import com.example.auth.domain.port.out.CookieServicePort;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserCasePort userCasePort;
    private final AuthCasePort authCasePort;
    private final CookieServicePort cookieService;

    public AuthController(UserCasePort userCasePort, AuthCasePort authCasePort, CookieServicePort cookieService) {
        this.userCasePort = userCasePort;
        this.authCasePort = authCasePort;
        this.cookieService = cookieService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(userCasePort.execute(request));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = authCasePort.executeLogin(request);
        cookieService.setAuthCookies(response, loginResponse.getAccessToken(), loginResponse.getRefreshToken());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody(required = false) RefreshTokenRequest request,
                                                             HttpServletRequest httpRequest,
                                                             HttpServletResponse response) {
        String refreshToken = null;
        if (request != null && request.refreshToken() != null && !request.refreshToken().isBlank()) {
            refreshToken = request.refreshToken();
        } else {
            refreshToken = cookieService.getRefreshToken(httpRequest);
        }

        RefreshTokenResponse tokenResponse = authCasePort.executeRefreshToken(new RefreshTokenRequest(refreshToken));
        cookieService.setAuthCookies(response, tokenResponse.accessToken(), tokenResponse.refreshToken());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/check-session")
    public ResponseEntity<CheckSessionResponse> checkSession(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            HttpServletRequest httpRequest) {
        String accessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7);
        } else {
            accessToken = cookieService.getAccessToken(httpRequest);
        }

        CheckSessionResponse sessionResponse = authCasePort.executeCheckSession(new CheckSessionRequest(accessToken));
        return ResponseEntity.ok(sessionResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieService.getRefreshToken(request);
        authCasePort.executeLogout(new RefreshTokenRequest(refreshToken));
        cookieService.clearAuthCookies(response);
        return ResponseEntity.ok().build();
    }
}