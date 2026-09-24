package com.gregory.api.users.domain.usecase;

import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import com.gregory.api.users.rest.dto.response.UserDataResponse;
import com.gregory.api.users.rest.dto.response.UserResponse;

import java.util.UUID;

public interface IUserUseCase {

    UserDataResponse getUsers(int page, int size);

    UserDataResponse findByUserId(String userId);

    void createUser(UserRequest request);

    UserResponse updateUser(UUID id, UserUpdateRequest request);

    void deleteUser(UUID id);
}