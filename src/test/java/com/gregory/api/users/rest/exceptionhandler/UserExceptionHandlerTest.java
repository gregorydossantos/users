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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.sql.SQLException;
import java.util.List;

import static com.gregory.api.users.domain.message.CommonsMessage.BAD_REQUEST;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_ALREADY_REGISTER;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserExceptionHandlerTest {
    public static final String IS_A_MANDATORY_FIELD = "Is a mandatory field!";
    public static final String INVALID_EMAIL = "Invalid email!";

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
        var exception = mock(MethodArgumentNotValidException.class);
        var bindingResult = mock(BindingResult.class);

        var nameError = new FieldError("userRequest", "name", IS_A_MANDATORY_FIELD);
        var emailError = new FieldError("userRequest", "email", INVALID_EMAIL);

        when(bindingResult.getAllErrors()).thenReturn(List.of(nameError, emailError));
        when(exception.getBindingResult()).thenReturn(bindingResult);

        var response = userExceptionHandler.handleValidationRequestBody(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertThat(IS_A_MANDATORY_FIELD.equalsIgnoreCase(response.getBody().get("name"))).isTrue();
        assertThat(INVALID_EMAIL.equalsIgnoreCase(response.getBody().get("email"))).isTrue();
        assertEquals(2, response.getBody().size());
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