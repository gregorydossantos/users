package com.gregory.api.users.rest.exceptionhandler;

import com.gregory.api.users.rest.exceptionhandler.exception.UserBadRequestException;
import com.gregory.api.users.rest.exceptionhandler.exception.UserDataIntegrityException;
import com.gregory.api.users.rest.exceptionhandler.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserDataIntegrityException.class)
    public ResponseEntity<ErrorResponse> userDataIntegrityException(final UserDataIntegrityException ex) {
        ErrorResponse errorResponse = new ErrorResponse("", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException(final UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserBadRequestException.class)
    public ResponseEntity<ErrorResponse> userBadRequestException(final UserBadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse("", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationRequestBody(final MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorResponse.setField(((FieldError) error).getField());
            errorResponse.setMessage(error.getDefaultMessage());
        });

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleAbsentParameter(final MissingServletRequestParameterException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getParameterName(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }
}