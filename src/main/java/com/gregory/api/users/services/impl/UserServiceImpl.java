package com.gregory.api.users.services.impl;

import com.gregory.api.users.domain.usecase.IUserUseCase;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import com.gregory.api.users.rest.dto.response.UserDataResponse;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.services.IUserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements IUserService {

    IUserUseCase userQueryUseCase;

    @Override
    public UserDataResponse getUsers(int page, int size) {
        return userQueryUseCase.getUsers(page, size);
    }

    @Override
    public UserDataResponse findByUserId(String userId) {
        return userQueryUseCase.findByUserId(userId);
    }

    @Override
    public void createUser(UserRequest request) {
        userQueryUseCase.createUser(request);
    }

    @Override
    public UserResponse updateUser(UUID id, UserUpdateRequest request) {
        return userQueryUseCase.updateUser(id, request);
    }

    @Override
    public void deleteUser(UUID id) {
        userQueryUseCase.deleteUser(id);
    }
}