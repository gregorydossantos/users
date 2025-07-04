package com.gregory.api.users.services.maintenance;

import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;

import java.util.UUID;

public interface IUserServiceMaintenance {

    void createUser(UserRequest request);

    UserResponse updateUser(String userId, UserRequest request);

    void deleteUser(String userId);
}