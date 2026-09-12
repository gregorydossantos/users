package com.gregory.api.users.domain.usecase.impl;

import com.gregory.api.users.domain.mapper.IUserMapper;
import com.gregory.api.users.infra.db.entities.UserEntity;
import com.gregory.api.users.infra.db.repositories.IUserRepository;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.exceptionhandler.exception.UserNotFoundException;
import com.gregory.api.users.services.encryption.IEncryptionService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserUseCaseImplTest {

    @Mock
    IUserRepository userRepositoryMock;

    @Mock
    IEncryptionService encryptionServiceMock;

    @Mock
    IUserMapper mapperMock;

    @InjectMocks
    UserUseCaseImpl userUseCaseImpl;

    UserRequest userRequestMock;
    UserUpdateRequest userUpdateRequestMock;
    UserResponse responseMock;
    UserEntity userMock;
    UUID idMock;

    @BeforeEach
    void setUp() {
        userRequestMock = UserRequest.builder()
                .name("Test")
                .email("test@test.com")
                .password("11111111")
                .exchangeCode(2)
                .build();

        userUpdateRequestMock = UserUpdateRequest.builder()
                .name("Update Test")
                .email("new_email@test.com")
                .build();

        responseMock = UserResponse.builder()
                .id(1L)
                .name("Test")
                .email("test@test.com")
                .password("11111111")
                .exchange("Mail")
                .build();

        userMock = UserEntity.builder()
                .id(1L)
                .name("Test")
                .email("test@test.com")
                .password("11111111")
                .exchange("Email")
                .build();

        idMock = UUID.randomUUID();
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Get a list of users successfully")
    void getUsers() {
        Page<UserEntity> mockPage = new PageImpl<>(List.of(mock(UserEntity.class)));

        when(userRepositoryMock.findAll(any(Pageable.class))).thenReturn(mockPage);
        when(mapperMock.toListResponse(anyList())).thenReturn(List.of(Mockito.mock(UserResponse.class)));

        var response = userUseCaseImpl.getUsers(0, 10);
        assertNotNull(response);
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Get one user successfully")
    void findByUserId() {
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Create a user")
    void shouldCreateAUser_When_CallCreateUser() {
        when(userRepositoryMock.findByEmail(userRequestMock.getEmail())).thenReturn(Optional.empty());
        when(mapperMock.toEntity(userRequestMock)).thenReturn(userMock);

        userUseCaseImpl.createUser(userRequestMock);
        verify(userRepositoryMock).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Update a user")
    void updateUser() {
        when(userRepositoryMock.findByUserId(String.valueOf(idMock))).thenReturn(Optional.ofNullable(userMock));
        when(mapperMock.toUpdate(any(), any())).thenReturn(userMock);
        when(mapperMock.toResponse(any())).thenReturn(responseMock);

        var response = userUseCaseImpl.updateUser(idMock, userUpdateRequestMock);
        assertNotNull(response);
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Delete a user")
    void deleteUser() {
        when(userRepositoryMock.findByUserId(String.valueOf(idMock))).thenReturn(Optional.ofNullable(userMock));

        userUseCaseImpl.deleteUser(idMock);
        verify(userRepositoryMock).delete(any(UserEntity.class));
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Not found any user")
    void should_ReturnsUserNotFoundException_When_UsersNotExists() {
        Page<UserEntity> mockPage = new PageImpl<>(Collections.emptyList());

        when(userRepositoryMock.findAll(any(Pageable.class))).thenReturn(mockPage);
        assertThrows(UserNotFoundException.class, () -> userUseCaseImpl.getUsers(0, 10));
    }
}