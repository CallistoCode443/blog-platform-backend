package com.example.blogplatform.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSummaryResponse {
    private UUID id;
    private String title;
    private UserSummaryResponse author;
    private CategoryResponse category;
    private List<TagResponse> tags;
    private LocalDateTime createdAt;
}
