package com.gregory.api.users.domain.usecase.maintenance;

import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;

import java.util.UUID;

public interface IUserUseCaseMaintenance {

    void createUser(UserRequest request);

    UserResponse updateUser(UUID id, UserRequest request);

    void deleteUser(UUID id);
}