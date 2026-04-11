package com.example.blogplatform.service;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogplatform.domain.dto.CreateUserRequest;
import com.example.blogplatform.domain.dto.LoginRequest;
import com.example.blogplatform.domain.dto.TokenBundle;
import com.example.blogplatform.domain.entity.RefreshToken;
import com.example.blogplatform.domain.entity.User;
import com.example.blogplatform.security.CookieManager;
import com.example.blogplatform.security.CustomUserDetails;
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
    private final AuthenticationManager authenticationManager;

    public TokenBundle login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return createTokenBundle(userDetails.getUser());
    }

    @Transactional
    public TokenBundle register(CreateUserRequest request) {
        User newUser = userService.create(request);

        return createTokenBundle(newUser);
    }

    @Transactional
    public TokenBundle refreshTokens(String refreshTokenFromCookie) {
        RefreshToken refreshToken = refreshTokenService.validateAndGetRefreshToken(refreshTokenFromCookie);
        refreshTokenService.delete(refreshToken);

        return createTokenBundle(refreshToken.getOwner());
    }

    private TokenBundle createTokenBundle(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.generateRefreshToken(user);

        ResponseCookie accessTokenCookie = cookieManager.createAccessTokenCookie(accessToken);
        ResponseCookie refreshTokenCookie = cookieManager.createRefreshTokenCookie(refreshToken);

        return TokenBundle.builder()
                .refreshTokenCookie(refreshTokenCookie)
                .accessTokenCookie(accessTokenCookie)
                .build();
    }
}
