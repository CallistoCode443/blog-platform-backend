package com.example.blogplatform.security;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.blogplatform.domain.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private Duration accessTokenDuraiton;

    private SecretKey secretKey;

    public JwtService(
            @Value("${app.jwt.access-token-duration}") Duration accessTokenDuration,
            @Value("${app.jwt.secret}") String secretPhrase) {
        this.accessTokenDuraiton = accessTokenDuration;
        this.secretKey = Keys.hmacShaKeyFor(secretPhrase.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = Instant.now().plus(accessTokenDuraiton);

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }

    public Claims extractClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }
}
