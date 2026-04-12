package com.example.blogplatform.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    @Size(min = 3, max = 255, message = "Username must be between {min} and {max} characters")
    private String username;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 3, max = 255, message = "Password must be between {min} and {max} characters")
    private String password;

    @NotBlank(message = "Current password is required")
    private String currentPassword;
}
