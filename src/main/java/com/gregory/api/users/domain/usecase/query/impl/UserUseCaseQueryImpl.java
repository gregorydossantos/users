package com.gregory.api.users.domain.usecase.query.impl;

import com.gregory.api.users.domain.mapper.IUserMapper;
import com.gregory.api.users.domain.usecase.query.IUserUseCaseQuery;
import com.gregory.api.users.infra.db.entities.Users;
import com.gregory.api.users.infra.db.repositories.IUserRepository;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.dto.response.UsersResponse;
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

import static com.gregory.api.users.domain.message.CommonsMessage.USER_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserUseCaseQueryImpl implements IUserUseCaseQuery {

    IUserRepository userRepository;
    IUserMapper userMapper;
    IEncryptionService encryptionService;

    @Override
    public UsersResponse getUsers(int page, int size) {
        log.info("Get all user from database");
        var users = userRepository.findAll(setPageable(page, size));

        log.info("Decrypting password field from all users");
        decryptingAllPasswords(users.getContent());

        log.info("Return all Users");
        var response = userMapper.toListUserResponse(users.getContent());
        return UsersResponse.builder().data(response).build();
    }

    @Override
    public UsersResponse findByUserId(String userId) {
        log.info("Get user by id: {}", userId);
        var user = userRepository.findByUserId(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        log.info("Decrypting user password");
        decryptingAllPasswords(List.of(user.get()));

        var response = userMapper.toListUserResponse(List.of(user.get()));

        log.info("Return User: {}", response.getFirst());
        return UsersResponse.builder().data(response).build();
    }

    private void decryptingAllPasswords(List<Users> users) {
        for (Users user : users) {
            log.debug("Encrypt password: {}", user.getPassword());
            user.setPassword(encryptionService.decrypt(user.getPassword()));
            log.debug("Decrypt password: {}", user.getPassword());
        }
    }

    private Pageable setPageable(int page, int size) {
        return PageRequest.of(page, size);
    }
}