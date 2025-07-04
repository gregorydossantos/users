package com.gregory.api.users.services.query.impl;

import com.gregory.api.users.domain.usecase.query.IUserUseCaseQuery;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.dto.response.UsersResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceQueryImplTest {

    @Mock
    IUserUseCaseQuery userQueryUseCase;

    @InjectMocks
    UserServiceQueryImpl userServiceQuery;

    @Test
    @DisplayName("SERVICE LAYER ::: Get a list of users successfully")
    void should_ReturnsAListOfUsers_When_CallGetUsers() {
        when(userQueryUseCase.getUsers(0,10)).thenReturn(Mockito.mock(UsersResponse.class));

        var response = userServiceQuery.getUsers(0, 10);
        assertNotNull(response);
    }

    @Test
    @DisplayName("SERVICE LAYER ::: Get a user successfully")
    void should_ReturnsAListOfUsers_When_CallFindByUserId() {
        when(userQueryUseCase.findByUserId(anyString())).thenReturn(Mockito.mock(UsersResponse.class));

        var response = userServiceQuery.findByUserId(UUID.randomUUID().toString());
        assertNotNull(response);
    }
}