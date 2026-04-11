package com.example.blogplatform.domain.dto;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePostRequest {
    @Size(min = 3, max = 255, message = "Title must be between {min} and {max} characters")
    private String title;

    @Size(min = 3, message = "Content cannot be empty")
    private String content;

    private UUID categoryId;

    @Builder.Default
    private Set<String> tags = null;
}
