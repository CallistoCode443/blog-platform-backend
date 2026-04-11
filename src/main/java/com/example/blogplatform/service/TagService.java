package com.example.blogplatform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogplatform.domain.dto.CreateTagRequest;
import com.example.blogplatform.domain.dto.TagResponse;
import com.example.blogplatform.domain.entity.Tag;
import com.example.blogplatform.exception.TagNotFoundException;
import com.example.blogplatform.mapper.TagMapper;
import com.example.blogplatform.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<TagResponse> getTags() {
        List<Tag> tags = tagRepository.findAll();
        return tagMapper.toResponseList(tags);
    }

    @Transactional
    public List<TagResponse> findOrCreateTags(CreateTagRequest request) {
        List<Tag> existingTags = tagRepository.findByNameIn(request.getNames());
        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        List<Tag> newTags = request.getNames().stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .build())
                .toList();

        List<Tag> result = new ArrayList<>(existingTags);
        if (!newTags.isEmpty()) {
            result.addAll(tagRepository.saveAll(newTags));
        }

        return tagMapper.toResponseList(result);
    }

    public void deleteTag(UUID id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(String.format("Tag with id %s not found", id)));

        tagRepository.delete(tag);
    }
}
