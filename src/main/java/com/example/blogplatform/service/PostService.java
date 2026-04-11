package com.example.blogplatform.service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogplatform.domain.PostStatus;
import com.example.blogplatform.domain.dto.CreatePostRequest;
import com.example.blogplatform.domain.dto.PostResponse;
import com.example.blogplatform.domain.dto.PostSummaryResponse;
import com.example.blogplatform.domain.dto.UpdatePostRequest;
import com.example.blogplatform.domain.entity.Category;
import com.example.blogplatform.domain.entity.Post;
import com.example.blogplatform.domain.entity.Tag;
import com.example.blogplatform.domain.entity.User;
import com.example.blogplatform.exception.PostNotFoundException;
import com.example.blogplatform.mapper.PostMapper;
import com.example.blogplatform.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CategoryService categoryService;
    private final TagService tagService;

    public Page<PostSummaryResponse> getPostSummaries(Pageable pageable) {
        return postRepository.findAll(pageable).map(postMapper::toSummaryResponse);
    }

    public PostResponse getPost(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s not found", id)));
        return postMapper.toResponse(post);
    }

    @Transactional
    public PostResponse createPost(CreatePostRequest request, User user) {
        Category category = categoryService.getCategoryById(request.getCategoryId());
        Set<Tag> tags = tagService.findOrCreateTags(request.getTags()).stream().collect(Collectors.toSet());

        Post newPost = Post.builder()
                .title(request.getTitle())
                .author(user)
                .content(request.getContent())
                .category(category)
                .status(PostStatus.DRAFT)
                .tags(tags)
                .build();

        postRepository.save(newPost);
        return postMapper.toResponse(newPost);
    }

    @Transactional
    public void deletePost(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s not found", id)));

        postRepository.delete(post);
    }

    @Transactional
    public void updatePost(UUID id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s not found", id)));

        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }

        if (request.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(request.getCategoryId());
            post.setCategory(category);
        }

        if (request.getTags() != null) {
            Set<Tag> tags = tagService.findOrCreateTags(request.getTags()).stream().collect(Collectors.toSet());
            post.setTags(tags);
        }

        postRepository.save(post);
    }

    @Transactional
    public void publishPost(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s not found", id)));

        post.setStatus(PostStatus.PUBLISHED);
        postRepository.save(post);
    }

    @Transactional
    public void unpublishPost(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s not found", id)));

        post.setStatus(PostStatus.DRAFT);
        postRepository.save(post);
    }
}
