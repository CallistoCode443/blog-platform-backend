package com.example.blogplatform.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.blogplatform.domain.dto.TagResponse;
import com.example.blogplatform.domain.entity.Tag;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    List<TagResponse> toResponseList(List<Tag> tags);
}
