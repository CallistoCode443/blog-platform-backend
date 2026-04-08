package com.example.blogplatform.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogplatform.domain.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    
}
