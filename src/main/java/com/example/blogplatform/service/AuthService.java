package com.example.blogplatform.service;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogplatform.domain.dto.CreateUserRequest;
import com.example.blogplatform.domain.dto.TokenBundle;
import com.example.blogplatform.domain.entity.RefreshToken;
import com.example.blogplatform.domain.entity.User;
import com.example.blogplatform.security.CookieManager;
import com.example.blogplatform.security.JwtService;
import com.example.blogplatform.security.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final CookieManager cookieManager;

    public void register(CreateUserRequest request) {
        User newUser = userService.create(request);
    }

    @Transactional
    public TokenBundle refreshTokens(String refreshTokenFromCookie) {
        RefreshToken refreshToken = refreshTokenService.validateAndGetRefreshToken(refreshTokenFromCookie);
        refreshTokenService.delete(refreshToken);

        String newRefreshToken = refreshTokenService.generateRefreshToken(refreshToken.getOwner());
        String newAccessToken = jwtService.generateAccessToken(refreshToken.getOwner());

        ResponseCookie refreshTokenCookie = cookieManager.createRefreshTokenCookie(newRefreshToken);
        ResponseCookie accessTokenCookie = cookieManager.createAccessTokenCookie(newAccessToken);

        return TokenBundle.builder()
                .refreshTokenCookie(refreshTokenCookie)
                .accessTokenCookie(accessTokenCookie)
                .build();
    }
}
