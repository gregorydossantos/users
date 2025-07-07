package com.gregory.api.users.rest.exceptionhandler;

import lombok.Data;

@Data
public class ErrorResponse {

    String field;
    String message;

    public ErrorResponse() {}

    public ErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }
}