package com.example.blogplatform.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogplatform.domain.dto.CategoryResponse;
import com.example.blogplatform.domain.dto.CreateCategoryRequest;
import com.example.blogplatform.domain.entity.Category;
import com.example.blogplatform.exception.CategoryAlreadyExistsException;
import com.example.blogplatform.exception.CategoryNotFoundException;
import com.example.blogplatform.mapper.CategoryMapper;
import com.example.blogplatform.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> listCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toResponseList(categories);
    }

    @Transactional
    public void createCategory(CreateCategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new CategoryAlreadyExistsException(
                    String.format("Category with name %s already exists", request.getName()));
        }

        Category category = Category.builder()
                .name(request.getName())
                .build();

        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id %s not found", id)));

        categoryRepository.delete(category);
    }
}
