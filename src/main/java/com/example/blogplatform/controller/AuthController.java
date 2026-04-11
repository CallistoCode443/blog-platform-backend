package com.example.blogplatform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogplatform.domain.dto.CreateUserRequest;
import com.example.blogplatform.domain.dto.LoginRequest;
import com.example.blogplatform.domain.dto.TokenBundle;
import com.example.blogplatform.security.CookieManager;
import com.example.blogplatform.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final CookieManager cookieManager;

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return buildTokenResponse(authService.register(createUserRequest), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest loginRequest) {
        return buildTokenResponse(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(value = "refresh_token", required = false) String refreshToken) {
        authService.logout(refreshToken);

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookieManager.clearAccessTokenCookie().toString())
                .header(HttpHeaders.SET_COOKIE, cookieManager.clearRefreshTokenCookie().toString())
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(@CookieValue(value = "refresh_token", required = false) String refreshToken) {
        return buildTokenResponse(authService.refreshTokens(refreshToken), HttpStatus.OK);
    }

    private ResponseEntity<Void> buildTokenResponse(TokenBundle tokenBundle, HttpStatus status) {
        return ResponseEntity.status(status)
                .header(HttpHeaders.SET_COOKIE,
                        cookieManager.createAccessTokenCookie(tokenBundle.getAccessToken()).toString())
                .header(HttpHeaders.SET_COOKIE,
                        cookieManager.createRefreshTokenCookie(tokenBundle.getRefreshToken()).toString())
                .build();
    }
}
