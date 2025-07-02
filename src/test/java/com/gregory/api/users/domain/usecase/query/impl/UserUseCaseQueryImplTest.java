package com.gregory.api.users.domain.usecase.query.impl;

import com.gregory.api.users.domain.mapper.IUserMapper;
import com.gregory.api.users.infra.db.entities.Users;
import com.gregory.api.users.infra.db.repositories.IUserRepository;
import com.gregory.api.users.rest.dto.response.UserResponse;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
}