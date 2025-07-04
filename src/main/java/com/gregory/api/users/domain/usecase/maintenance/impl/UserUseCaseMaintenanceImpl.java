package com.gregory.api.users.domain.usecase.maintenance.impl;

import com.gregory.api.users.domain.mapper.IUserMapper;
import com.gregory.api.users.domain.usecase.maintenance.IUserUseCaseMaintenance;
import com.gregory.api.users.infra.db.entities.Users;
import com.gregory.api.users.infra.db.repositories.IUserRepository;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.exceptionhandler.exception.UserDataIntegrityException;
import com.gregory.api.users.rest.exceptionhandler.exception.UserNotFoundException;
import com.gregory.api.users.services.encryption.IEncryptionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.gregory.api.users.domain.message.CommonsMessage.USER_ALREADY_REGISTER;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserUseCaseMaintenanceImpl implements IUserUseCaseMaintenance {

    IUserRepository userRepository;
    IUserMapper mapper;
    IEncryptionService encryptionService;

    @Override
    public void createUser(UserRequest request) {
        log.info("====[CREATE USER]====");
        log.info("Validate if user already exists by email {}", request.getEmail());
        if (userExists(request.getEmail())) {
            throw new UserDataIntegrityException(USER_ALREADY_REGISTER);
        }

        log.info("[CREATE] ==== Initialize encryption password field");
        encryptPassword(request);

        log.info("Convert request in entity");
        var user = mapper.toEntity(request);

        log.info("Creating user_id in the user");
        setUserId(user);

        log.info("Persist entity at database: {}", user);
        userRepository.save(user);
    }

    @Override
    public UserResponse updateUser(String userId, UserRequest request) {
        log.info("====[UPDATE USER]====");
        log.info("Get register by user id {} in database", userId);
        var oldUser = userRepository.findByUserId(userId);

        log.info("[UPDATE] ==== Validating if exists register on database");
        if (oldUser.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        log.info("[UPDATE] ==== Initialize encryption password field");
        encryptPassword(request);

        log.info("Update old user");
        var userUpdate = mapper.toUpdate(oldUser.get(), request);
        log.info("Saving updated user: {}", userUpdate);
        userRepository.save(userUpdate);

        return mapper.toResponse(userUpdate);
    }

    @Override
    public void deleteUser(String userId) {
        log.info("====[DELETE USER]====");
        log.info("Get user by ID {}", userId);
        var user = userRepository.findByUserId(userId);

        log.info("[DELETE] ==== Validating if exists register on database");
        if (user.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        log.info("Deleting user from database");
        userRepository.delete(user.get());
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void encryptPassword(UserRequest request) {
        log.info("Encrypting user password");
        request.setPassword(encryptionService.encrypt(request.getPassword()));
    }

    private void setUserId(Users user) {
        user.setUserId(UUID.randomUUID().toString());
    }
}