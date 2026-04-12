package com.example.blogplatform.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogplatform.domain.dto.CreateUserRequest;
import com.example.blogplatform.domain.dto.UpdateUserRequest;
import com.example.blogplatform.domain.dto.UserResponse;
import com.example.blogplatform.domain.entity.User;
import com.example.blogplatform.exception.InvalidPasswordException;
import com.example.blogplatform.exception.UserAlreadyExistsException;
import com.example.blogplatform.exception.UserNotFoundException;
import com.example.blogplatform.mapper.UserMapper;
import com.example.blogplatform.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public User create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    String.format("User with email '%s' already exists", request.getEmail()));
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(
                    String.format("User with username '%s' already exists", request.getUsername()));
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(newUser);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' not found", id, null)));
    }

    @Transactional
    public UserResponse updateCurrentUser(UpdateUserRequest request, User currentUser) {
        if (request.getUsername() != null) {
            if (userRepository.existsByUsername(request.getUsername())
                    && !currentUser.getUsername().equals(request.getUsername())) {
                throw new UserAlreadyExistsException(
                        String.format("User with username '%s' already exists", request.getUsername()));
            }
            currentUser.setUsername(request.getUsername());
        }

        if (request.getEmail() != null) {
            if (userRepository.existsByEmail(request.getEmail())
                    && !currentUser.getEmail().equals(request.getEmail())) {
                throw new UserAlreadyExistsException(
                        String.format("User with email '%s' already exists", request.getEmail()));
            }
            currentUser.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && request.getCurrentPassword() != null) {
            if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword())) {
                throw new InvalidPasswordException("Current password is incorrect");
            }
            currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userMapper.toResponse(userRepository.save(currentUser));
    }
}
