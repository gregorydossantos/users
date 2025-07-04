package com.gregory.api.users.domain.usecase.query.impl;

import com.gregory.api.users.domain.mapper.IUserMapper;
import com.gregory.api.users.infra.db.entities.Users;
import com.gregory.api.users.infra.db.repositories.IUserRepository;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.dto.response.UsersResponse;
import com.gregory.api.users.rest.exceptionhandler.exception.UserNotFoundException;
import com.gregory.api.users.services.encryption.IEncryptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserUseCaseQueryImplTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    IEncryptionService encryptionService;

    @Mock
    IUserMapper mapper;

    @InjectMocks
    UserUseCaseQueryImpl useCase;

    @Test
    @DisplayName("USE CASE LAYER ::: Get a list of users successfully")
    void should_ReturnsAListOfUsers_When_CallGetUsers() {
        Page<Users> mockPage = new PageImpl<>(List.of(mock(Users.class)));

        when(userRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
        when(mapper.toListUserResponse(anyList())).thenReturn(List.of(Mockito.mock(UserResponse.class)));

        var response = useCase.getUsers(0, 10);
        assertNotNull(response);
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Get a user by user_id")
    void should_ReturnAUser_When_CallFinByUserId() {
        var user = buildUser();
        var userResponse = buildUserResponse(user);
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(mapper.toListUserResponse(List.of(user))).thenReturn(List.of(userResponse));

        var response = useCase.findByUserId(user.getUserId());
        assertNotNull(response);
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Throw user not found exception")
    void should_ReturnUserNotFoundException_When_CallFinByUserIdFailed() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> useCase.findByUserId(UUID.randomUUID().toString()));
    }

    private Users buildUser() {
        return Users.builder()
                .id(1L)
                .userId(UUID.randomUUID().toString())
                .name("Test")
                .email("test@test.com")
                .password("123456")
                .exchange("email")
                .build();
    }

    private UserResponse buildUserResponse(Users users) {
        return UserResponse.builder()
                .id(users.getId())
                .userId(users.getUserId())
                .name(users.getName())
                .email(users.getEmail())
                .password(users.getPassword())
                .exchange(users.getExchange())
                .build();
    }
}