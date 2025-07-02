package com.gregory.api.users.rest.exceptionhandler.exception;

public class UserDataIntegrityException extends RuntimeException {

    public UserDataIntegrityException(String message) {
        super(message);
    }
}