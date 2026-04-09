package com.example.blogplatform.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters фтв numbers")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
