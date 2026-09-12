package com.gregory.api.users.rest.controller.impl;

import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import com.gregory.api.users.rest.dto.response.UserDataResponse;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.services.IUserService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static com.gregory.api.users.rest.path.Routes.PATH_USERS;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerImplTest {
    private static final String PATH_USERS_ID = PATH_USERS + "/" + UUID.randomUUID();

    @LocalServerPort
    int port;

    @MockitoBean
    IUserService userServiceMock;

    UserDataResponse response;

    UUID idMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        response = mock(UserDataResponse.class);
        RestAssured.port = port;
        idMock = UUID.randomUUID();
    }

    @Test
    @DisplayName("CONTROLLER LAYER ::: Should be return a HTTP status 200 - SUCCESS")
    void getAllUsers() {
        when(userServiceMock.getUsers(0, 10)).thenReturn(response);

        given()
                .when().get(PATH_USERS)
                .then().statusCode(HttpStatus.OK.value());

        verify(userServiceMock).getUsers(anyInt(), anyInt());
    }

    @Test
    @DisplayName("CONTROLLER LAYER ::: Should be return a HTTP status 200 - SUCCESS")
    void getOneUser() {
        when(userServiceMock.findByUserId(anyString())).thenReturn(response);

        given()
                .param("user_id", UUID.randomUUID().toString())
                .when().get(PATH_USERS)
                .then().statusCode(HttpStatus.OK.value());

        verify(userServiceMock).findByUserId(anyString());
    }

    @Test
    @DisplayName("REST LAYER ::: Should be return a http status 201 - CREATED")
    void createUser() {
        var request = UserRequest.builder()
                .name("Test")
                .email("test@test.com")
                .password("Test")
                .exchangeCode(0)
                .build();
        doNothing().when(userServiceMock).createUser(request);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(PATH_USERS)
                .then().statusCode(HttpStatus.CREATED.value());

        verify(userServiceMock).createUser(any(UserRequest.class));
    }

    @Test
    @DisplayName("REST LAYER ::: Should be return a http status 200 - SUCCESS")
    void updateUser() {
        var request = mock(UserUpdateRequest.class);
        var response = mock(UserResponse.class);
        when(userServiceMock.updateUser(idMock, request)).thenReturn(response);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().patch(PATH_USERS_ID)
                .then().statusCode(HttpStatus.OK.value());

        verify(userServiceMock).updateUser(any(UUID.class), any(UserUpdateRequest.class));
    }

    @Test
    @DisplayName("REST LAYER ::: Should be return a http status 200 - SUCCESS")
    void deleteUser() {
        doNothing().when(userServiceMock).deleteUser(idMock);

        given()
                .contentType(ContentType.JSON)
                .when().delete(PATH_USERS_ID)
                .then().statusCode(HttpStatus.OK.value());

        verify(userServiceMock).deleteUser(any(UUID.class));
    }
}