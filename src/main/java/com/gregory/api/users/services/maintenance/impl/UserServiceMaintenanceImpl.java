package com.gregory.api.users.services.maintenance.impl;

import com.gregory.api.users.domain.usecase.maintenance.IUserUseCaseMaintenance;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.services.maintenance.IUserServiceMaintenance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceMaintenanceImpl implements IUserServiceMaintenance {

    IUserUseCaseMaintenance userMaintenanceUseCase;

    @Override
    public void createUser(UserRequest request) {
        userMaintenanceUseCase.createUser(request);
    }

    @Override
    public UserResponse updateUser(UUID id, UserRequest request) {
        return userMaintenanceUseCase.updateUser(id, request);
    }

    @Override
    public void deleteUser(UUID id) {
        userMaintenanceUseCase.deleteUser(id);
    }
}