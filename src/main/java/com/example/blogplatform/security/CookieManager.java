package com.example.blogplatform.security;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.example.blogplatform.property.CookieProperties;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieManager {
    private final CookieProperties cookieProperties;

    public ResponseCookie createAccessTokenCookie(String token) {
        return createCookie(cookieProperties.getAccessTokenName(), token, cookieProperties.getPath());
    }

    public ResponseCookie clearAccessTokenCookie() {
        return clearCookie(cookieProperties.getAccessTokenName(), cookieProperties.getPath());
    }

    public String getAccessTokenFromCookie(HttpServletRequest request) {
        return getCookieValue(request, cookieProperties.getAccessTokenName());
    }

    public ResponseCookie createRefreshTokenCookie(String token) {
        return createCookie(cookieProperties.getRefreshTokenName(), token, cookieProperties.getPath());
    }

    public ResponseCookie clearRefreshTokenCookie() {
        return clearCookie(cookieProperties.getRefreshTokenName(), cookieProperties.getPath());
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        return getCookieValue(request, cookieProperties.getRefreshTokenName());
    }

    private ResponseCookie createCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value)
                .path(path)
                .httpOnly(true)
                .sameSite("strict")
                .maxAge(cookieProperties.getMaxAge())
                .build();
    }

    private ResponseCookie clearCookie(String name, String path) {
        return ResponseCookie.from(name, "")
                .path(path)
                .httpOnly(true)
                .maxAge(0)
                .build();
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }
}
