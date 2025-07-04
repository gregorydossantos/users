package com.gregory.api.users.rest.query;

import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.dto.response.UsersResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface IUserControllerQuery {

    @GetMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<UsersResponse> getUsers(@RequestParam(value = "user_id", required = false) String userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size);
}