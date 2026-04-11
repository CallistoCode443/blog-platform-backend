package com.example.blogplatform.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequest {
    @NotBlank(message = "Category name is required")
    @Pattern(regexp = "^[a-zA-Z0-9-_ +]+$", message = "can only contain letters, numbers, hyphens, underscores, plus and spaces")
    private String name;
}
