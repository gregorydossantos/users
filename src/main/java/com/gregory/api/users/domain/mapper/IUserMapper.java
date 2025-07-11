package com.gregory.api.users.domain.mapper;

import com.gregory.api.users.infra.db.entities.Users;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    Users toEntity(UserRequest request);

    UserResponse toResponse(Users user);

    List<UserResponse> toListUserResponse(List<Users> users);
}