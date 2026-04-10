package com.example.blogplatform.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogplatform.domain.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    boolean existsByToken(String token);
    Optional<RefreshToken> findByToken(String token);
}
