package com.gregory.api.users.domain.usecase.maintenance.impl;

import com.gregory.api.users.domain.mapper.IUserMapper;
import com.gregory.api.users.infra.db.entities.Users;
import com.gregory.api.users.infra.db.repositories.IUserRepository;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.exceptionhandler.exception.UserDataIntegrityException;
import com.gregory.api.users.rest.exceptionhandler.exception.UserNotFoundException;
import com.gregory.api.users.services.encryption.IEncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserUseCaseMaintenanceImplTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    IUserMapper mapper;

    @Mock
    IEncryptionService encryptionService;

    @InjectMocks
    UserUseCaseMaintenanceImpl userMaintenanceUseCase;

    UserRequest requestMock;
    UserResponse responseMock;
    Users userMock;

    @BeforeEach
    void setUp() {
        requestMock = UserRequest.builder()
                .name("Test")
                .email("test@test.com")
                .password("11111111")
                .exchange("Mail")
                .build();

        responseMock = UserResponse.builder()
                .id(1L)
                .userId(UUID.randomUUID().toString())
                .name("Test")
                .email("test@test.com")
                .password("11111111")
                .exchange("Mail")
                .build();

        userMock = Users.builder()
                .id(1L)
                .userId(UUID.randomUUID().toString())
                .name("Test")
                .email("test@test.com")
                .password("11111111")
                .exchange("Mail")
                .build();
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Create a user")
    void shouldCreateAUser_When_CallCreateUser() {
        when(userRepository.findByEmail(requestMock.getEmail())).thenReturn(Optional.empty());
        when(mapper.toEntity(requestMock)).thenReturn(userMock);

        userMaintenanceUseCase.createUser(requestMock);
        verify(userRepository).save(any(Users.class));
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Update a user")
    void updateUser() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.ofNullable(userMock));
        when(mapper.toUpdate(any(), any())).thenReturn(userMock);
        when(mapper.toResponse(any())).thenReturn(responseMock);

        var response = userMaintenanceUseCase.updateUser(UUID.randomUUID().toString(), requestMock);
        assertNotNull(response);
    }

    @Test
    @DisplayName("USE CASE LAYER ::: Delete a user")
    void deleteUser() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.ofNullable(userMock));

        userMaintenanceUseCase.deleteUser(UUID.randomUUID().toString());
        verify(userRepository).delete(any(Users.class));
    }

    @Test
    @DisplayName("USE CASE LAYER ::: UserDataIntegrityException")
    void throwUserDataIntegrityException_When_CreateAUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(userMock));
        assertThrows(UserDataIntegrityException.class, () -> userMaintenanceUseCase.createUser(requestMock));
    }

    @Test
    @DisplayName("USE CASE LAYER ::: UserNotFoundException [UPDATE]")
    void throwUserNotFoundException_When_UpdateAUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userMaintenanceUseCase.updateUser(UUID.randomUUID().toString(), requestMock));
    }

    @Test
    @DisplayName("USE CASE LAYER ::: UserNotFoundException [DELETE]")
    void throwUserNotFoundException_When_DeleteAUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userMaintenanceUseCase.deleteUser(UUID.randomUUID().toString()));
    }
}