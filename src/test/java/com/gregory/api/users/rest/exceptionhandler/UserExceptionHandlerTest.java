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
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.sql.SQLException;

import static com.gregory.api.users.domain.message.CommonsMessage.BAD_REQUEST;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_ALREADY_REGISTER;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

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
    @DisplayName("EXCEPTION HANDLER ::: Should be return UserDataIntegrityException")
    void userDataIntegrityException() {
        ResponseEntity<ErrorResponse> response = userExceptionHandler.userDataIntegrityException(
                new UserDataIntegrityException(USER_ALREADY_REGISTER));

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("EXCEPTION HANDLER ::: Should be return UserNotFoundException")
    void userNotFoundException() {
        ResponseEntity<ErrorResponse> response = userExceptionHandler.userNotFoundException(
                new UserNotFoundException(USER_NOT_FOUND));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("EXCEPTION HANDLER ::: Should be return UserBadRequestException")
    void userBadRequestException() {
        ResponseEntity<ErrorResponse> response = userExceptionHandler.userBadRequestException(
                new UserBadRequestException(BAD_REQUEST));

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("EXCEPTION HANDLER ::: Should be return MethodArgumentNotValidException")
    void handleValidationRequestBody() {
        var parameter = mock(MethodParameter.class);
        var bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);
        ResponseEntity<ErrorResponse> response = userExceptionHandler.handleValidationRequestBody(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("EXCEPTION HANDLER ::: Should be return MissingServletRequestParameterException")
    void handleAbsentParameter() {
        var mock = mock(MissingServletRequestParameterException.class);
        ResponseEntity<ErrorResponse> response = userExceptionHandler.handleAbsentParameter(mock);
        assertNotNull(response);
    }

    @Test
    @DisplayName("EXCEPTION HANDLER ::: Should be return SQLException")
    void handlerSqlException() {
        var mock = mock(SQLException.class);
        ResponseEntity<ErrorResponse> response = userExceptionHandler.handlerSqlException(mock);
        assertNotNull(response);
    }
}