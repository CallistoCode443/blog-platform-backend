package com.example.blogplatform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogplatform.domain.dto.CreateUserRequest;
import com.example.blogplatform.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        authService.register(createUserRequest);
        return ResponseEntity.ok().build();
    }
}
