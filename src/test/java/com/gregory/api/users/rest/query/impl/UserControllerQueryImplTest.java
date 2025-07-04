package com.gregory.api.users.rest.query.impl;

import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.dto.response.UsersResponse;
import com.gregory.api.users.services.query.IUserServiceQuery;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerQueryImplTest {

    @LocalServerPort
    int port;

    @MockitoBean
    IUserServiceQuery userServiceQuery;

    UsersResponse usersResponseList;
    UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usersResponseList = Mockito.mock(UsersResponse.class);
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should be return a HTTP status 200 - Return list of Users")
    void should_ReturnsSuccess_When_GetUsers() {
        when(userServiceQuery.getUsers(0,10)).thenReturn(usersResponseList);

        given()
                .contentType(ContentType.JSON)
                .when().get(PATH_USERS)
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should be return a HTTP status 200 - Return a User")
    void should_ReturnsSuccess_When_FindByUserId() {
        when(userServiceQuery.findByUserId(anyString())).thenReturn(usersResponseList);

        given()
                .contentType(ContentType.JSON)
                .param("user_id", UUID.randomUUID().toString())
                .when().get(PATH_USERS)
                .then().statusCode(HttpStatus.OK.value());
    }
}