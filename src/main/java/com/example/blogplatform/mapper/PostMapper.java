package com.example.blogplatform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.blogplatform.domain.dto.PostResponse;
import com.example.blogplatform.domain.dto.PostSummaryResponse;
import com.example.blogplatform.domain.entity.Post;

@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE, 
        uses = { CategoryMapper.class, TagMapper.class, UserMapper.class })
public interface PostMapper {
    PostSummaryResponse toSummaryResponse(Post post);
    PostResponse toResponse(Post post);
}
