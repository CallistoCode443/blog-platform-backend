package com.example.blogplatform.service;

import org.springframework.stereotype.Service;

import com.example.blogplatform.domain.dto.CreateUserRequest;
import com.example.blogplatform.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    public void register(CreateUserRequest request) {
        User newUser = userService.create(request);
    }
}
