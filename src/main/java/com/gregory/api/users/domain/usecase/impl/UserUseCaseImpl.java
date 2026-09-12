package com.gregory.api.users.domain.usecase.impl;

import com.gregory.api.users.domain.mapper.IUserMapper;
import com.gregory.api.users.domain.usecase.IUserUseCase;
import com.gregory.api.users.infra.db.entities.UserEntity;
import com.gregory.api.users.infra.db.repositories.IUserRepository;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import com.gregory.api.users.rest.dto.response.UserDataResponse;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.exceptionhandler.exception.UserDataIntegrityException;
import com.gregory.api.users.rest.exceptionhandler.exception.UserNotFoundException;
import com.gregory.api.users.services.encryption.IEncryptionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_ALREADY_REGISTER;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_NOT_FOUND;
import static com.gregory.api.users.useful.StringUseful.nonNullOrEmpty;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserUseCaseImpl implements IUserUseCase {

    IUserRepository userRepository;
    IUserMapper userMapper;
    IEncryptionService encryptionService;

    @Override
    public UserDataResponse getUsers(int page, int size) {
        log.info("Get all user from database");
        var users = userRepository.findAll(setPageable(page, size));

        log.info("Decrypting password field from all users");
        decryptingAllPasswords(users.getContent());

        log.info("Return all Users");
        return UserDataResponse.builder()
                .users(userMapper.toListResponse(users.getContent()))
                .build();
    }

    @Override
    public UserDataResponse findByUserId(String userId) {
        log.info("Get user by id: {}", userId);
        var user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        log.info("Decrypting user password");
        decryptingAllPasswords(List.of(user.get()));

        var response = userMapper.toListResponse(List.of(user.get()));

        log.info("Return User: {}", response.getFirst());
        return UserDataResponse.builder().users(response).build();
    }

    @Override
    public void createUser(UserRequest request) {
        log.info("====[CREATE USER]====");
        log.info("Validate if user already exists by email {}", request.getEmail());
        if (userExists(request.getEmail())) {
            throw new UserDataIntegrityException(EMAIL_ALREADY_REGISTER);
        }

        log.info("[CREATE] ==== Initialize encryption password field");
        encryptPassword(request);

        log.info("Convert request in entity");
        var user = userMapper.toEntity(request);

        log.info("Persist entity at database: {}", user);
        userRepository.save(user);
    }

    @Override
    public UserResponse updateUser(UUID id, UserUpdateRequest request) {
        log.info("====[UPDATE USER]====");
        log.info("Get register by ID {} in database", id);
        var oldUser = userRepository.findByUserId(id.toString());

        log.info("[UPDATE] ==== Validating if exists register on database");
        if (oldUser.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        log.info("[UPDATE] ==== Update encryption password field");
        if (nonNullOrEmpty(request.getPassword())) {
            request.setPassword(encryptionService.encrypt(request.getPassword()));
        }

        log.info("Update old user");
        var userUpdate = userMapper.toUpdate(oldUser.get(), request);
        log.info("Saving updated user: {}", userUpdate);
        userRepository.save(userUpdate);

        return userMapper.toResponse(userUpdate);
    }

    @Override
    public void deleteUser(UUID id) {
        log.info("====[DELETE USER]====");
        log.info("Get user by ID {}", id);
        var user = userRepository.findByUserId(id.toString());

        log.info("[DELETE] ==== Validating if exists register on database");
        if (user.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        log.info("Deleting user from database");
        userRepository.delete(user.get());
    }

    private Pageable setPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void encryptPassword(UserRequest request) {
        log.info("Encrypting user password");
        request.setPassword(encryptionService.encrypt(request.getPassword()));
    }

    private void decryptingAllPasswords(List<UserEntity> users) {
        for (UserEntity user : users) {
            log.debug("Encrypt password: {}", user.getPassword());
            user.setPassword(encryptionService.decrypt(user.getPassword()));
            log.debug("Decrypt password: {}", user.getPassword());
        }
    }
}