package com.example.blogplatform.security;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.blogplatform.domain.entity.RefreshToken;
import com.example.blogplatform.domain.entity.User;
import com.example.blogplatform.exception.RefreshTokenExpiredException;
import com.example.blogplatform.repository.RefreshTokenRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RefreshTokenService {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final RefreshTokenRepository refreshTokenRepository;

    private final Duration refreshTokenDuration;

    public RefreshTokenService(
            @Value("${app.jwt.refresh-token-duration}") Duration refreshTokenDuration,
            RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenDuration = refreshTokenDuration;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateRefreshToken(User user) {
        LocalDateTime expiresAt = LocalDateTime.now().plus(refreshTokenDuration);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(generateRawToken())
                .owner(user)
                .expiresAt(expiresAt)
                .build();

        refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }

    public RefreshToken validateAndGetRefreshToken(String rawRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(rawRefreshToken)
                .orElseThrow(() -> new EntityNotFoundException("Refresh Token not found"));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenExpiredException("Refresh Token expired");
        }

        return refreshToken;
    }

    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    private String generateRawToken() {
        byte[] bytes = new byte[32];
        String token;

        do {
            SECURE_RANDOM.nextBytes(bytes);
            token = Base64.getEncoder().withoutPadding().encodeToString(bytes);
        } while (refreshTokenRepository.existsByToken(token));

        return token;
    }
}
