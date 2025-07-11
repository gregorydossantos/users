package com.gregory.api.users.rest.maintenance.impl;

import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.services.maintenance.IUserServiceMaintenance;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerMaintenanceImplTest {

    @LocalServerPort
    int port;

    @MockitoBean
    IUserServiceMaintenance serviceMaintenance;

    UserRequest request;
    String id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RestAssured.port = port;
        id = UUID.randomUUID().toString();
        request = UserRequest.builder()
                .name("Test")
                .email("test@test.com")
                .password("test-123")
                .exchange("person")
                .build();
    }

    @Test
    @DisplayName("REST LAYER ::: Should be return a http status 201 - CREATED")
    void should_ReturnsHttp201_When_CreateUser() {
        doNothing().when(serviceMaintenance).createUser(any(UserRequest.class));

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(PATH_USERS)
                .then().statusCode(HttpStatus.CREATED.value());

        verify(serviceMaintenance).createUser(any(UserRequest.class));
    }

    @Test
    @DisplayName("REST LAYER ::: Should be return a http status 200 - SUCCESS")
    void should_ReturnsHttp200_When_UpdateUser() {
        var response = Mockito.mock(UserResponse.class);
        when(serviceMaintenance.updateUser(id, request)).thenReturn(response);

        given()
                .contentType(ContentType.JSON)
                .param("user_id", id)
                .body(request)
                .when().put(PATH_USERS)
                .then().statusCode(HttpStatus.OK.value());

        verify(serviceMaintenance).updateUser(anyString(), any(UserRequest.class));
    }

    @Test
    @DisplayName("REST LAYER ::: Should be return a http status 200 - SUCCESS")
    void should_ReturnsHttp200_When_DeleteUser() {
        doNothing().when(serviceMaintenance).deleteUser(id);

        given()
                .contentType(ContentType.JSON)
                .param("user_id", id)
                .when().delete(PATH_USERS)
                .then().statusCode(HttpStatus.OK.value());

        verify(serviceMaintenance).deleteUser(id);
    }
}