package com.gregory.api.users.rest.exceptionhandler.exception;

public class UserBadRequestException extends RuntimeException {

    public UserBadRequestException(String message) {
        super(message);
    }
}