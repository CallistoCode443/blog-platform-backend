package com.example.blogplatform.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogplatform.domain.dto.CreateUserRequest;
import com.example.blogplatform.domain.entity.User;
import com.example.blogplatform.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    String.format("User with email '%s' already exists", request.getEmail()));
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException(
                    String.format("User with username '%s' already exists", request.getUsername()));
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(newUser);
    }
}
