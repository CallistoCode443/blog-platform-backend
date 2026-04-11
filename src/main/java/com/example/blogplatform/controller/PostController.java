package com.example.blogplatform.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogplatform.domain.dto.CreatePostRequest;
import com.example.blogplatform.domain.dto.PostResponse;
import com.example.blogplatform.domain.dto.PostSummaryResponse;
import com.example.blogplatform.domain.dto.UpdatePostRequest;
import com.example.blogplatform.security.CustomUserDetails;
import com.example.blogplatform.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostSummaryResponse>> listPosts(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostSummaryResponse> postsSummary = postService.getPostSummaries(pageable);
        return ResponseEntity.ok(postsSummary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable UUID id) {
        PostResponse postResponse = postService.getPost(id);
        return ResponseEntity.ok(postResponse);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println("userDetails = " + userDetails);
        System.out.println("SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());
        PostResponse postResponse = postService.createPost(request, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable UUID id, @RequestBody UpdatePostRequest request) {
        postService.updatePost(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<Void> publishPost(@PathVariable UUID id) {
        postService.publishPost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PostMapping("/{id}/unpublish")
    public ResponseEntity<Void> unpublishPost(@PathVariable UUID id) {
        postService.unpublishPost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
