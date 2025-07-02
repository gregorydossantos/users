package com.gregory.api.users.rest.exceptionhandler;

import com.gregory.api.users.rest.exceptionhandler.exception.UserBadRequestException;
import com.gregory.api.users.rest.exceptionhandler.exception.UserDataIntegrityException;
import com.gregory.api.users.rest.exceptionhandler.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static com.gregory.api.users.domain.message.CommonsMessage.BAD_REQUEST;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_ALREADY_REGISTER;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class UserExceptionHandlerTest {

    @InjectMocks
    UserExceptionHandler userExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should be return UserDataIntegrityException")
    void should_ReturnsUserDataIntegrityException_When_UserAlreadyExists() {
        ResponseEntity<ErrorResponse> response = userExceptionHandler.userDataIntegrityException(
                new UserDataIntegrityException(USER_ALREADY_REGISTER));

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Should be return UserNotFoundException")
    void should_ReturnsUserNotFoundException_When_UserNotFound() {
        ResponseEntity<ErrorResponse> response = userExceptionHandler.userNotFoundException(
                new UserNotFoundException(USER_NOT_FOUND));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Should be return UserNotFoundException")
    void should_ReturnsUserBadRequestException_When_UserBadRequest() {
        ResponseEntity<ErrorResponse> response = userExceptionHandler.userBadRequestException(
                new UserBadRequestException(BAD_REQUEST));

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}