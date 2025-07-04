package com.gregory.api.users.domain.mapper;

import com.gregory.api.users.infra.db.entities.Users;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    Users toEntity(UserRequest request);

    UserResponse toResponse(Users user);

    List<UserResponse> toListUserResponse(List<Users> users);

    @Mapping(target = "id", ignore = true)
    Users toUpdate(@MappingTarget Users user, UserRequest request);
}