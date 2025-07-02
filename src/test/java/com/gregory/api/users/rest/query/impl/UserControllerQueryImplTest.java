package com.gregory.api.users.rest.query.impl;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static com.gregory.api.users.rest.path.Routes.PATH_USERS;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserQueryControllerImplTest {

    @LocalServerPort
    int port;

    @MockBean
    IUserServiceQuery userServiceQuery;

    UsersResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userResponse = Mockito.mock(UsersResponse.class);
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should be return a HTTP status 200 - SUCCESS")
    void should_ReturnsSuccess_When_GetUsers() {
        when(userServiceQuery.getUsers(0,10)).thenReturn(userResponse);

        given()
                .contentType(ContentType.JSON)
                .when().get(PATH_USERS)
                .then().statusCode(HttpStatus.OK.value());
    }
}