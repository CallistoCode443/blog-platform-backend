package com.example.blogplatform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.blogplatform.domain.dto.UserSummaryResponse;
import com.example.blogplatform.domain.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserSummaryResponse toSummaryResponse(User user);
}
