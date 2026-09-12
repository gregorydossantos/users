package com.gregory.api.users.services.impl;

import com.gregory.api.users.domain.usecase.IUserUseCase;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import com.gregory.api.users.rest.dto.response.UserDataResponse;
import com.gregory.api.users.rest.dto.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    IUserUseCase userUserCaseMock;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("SERVICE LAYER ::: Get a list of users successfully")
    void getAllUsers() {
        when(userUserCaseMock.getUsers(0, 10)).thenReturn(Mockito.mock(UserDataResponse.class));

        var response = userService.getUsers(0, 10);
        assertNotNull(response);
    }

    @Test
    @DisplayName("SERVICE LAYER ::: Get one user by user_id")
    void getOneUser() {
    }

    @Test
    @DisplayName("SERVICE LAYER ::: Create a user successfully")
    void createUser() {
        var request = Mockito.mock(UserRequest.class);
        doNothing().when(userUserCaseMock).createUser(request);

        userService.createUser(request);
        verify(userUserCaseMock).createUser(request);
    }

    @Test
    @DisplayName("SERVICE LAYER ::: Update a user successfully")
    void updateUser() {
        var id = UUID.randomUUID();
        var request = Mockito.mock(UserUpdateRequest.class);
        var response = Mockito.mock(UserResponse.class);
        when(userUserCaseMock.updateUser(id, request)).thenReturn(response);

        var data = userService.updateUser(id, request);
        assertNotNull(data);
    }

    @Test
    @DisplayName("SERVICE LAYER ::: Delete a user successfully")
    void deleteUser() {
        var id = UUID.randomUUID();
        doNothing().when(userUserCaseMock).deleteUser(id);

        userService.deleteUser(id);
        verify(userUserCaseMock).deleteUser(id);
    }
}