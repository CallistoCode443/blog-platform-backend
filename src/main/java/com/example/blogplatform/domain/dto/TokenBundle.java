package com.example.blogplatform.domain.dto;

import org.springframework.http.ResponseCookie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenBundle {
    private ResponseCookie accessTokenCookie;
    private ResponseCookie refreshTokenCookie;
}
