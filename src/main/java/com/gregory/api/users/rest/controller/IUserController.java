package com.gregory.api.users.rest.controller;

import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import com.gregory.api.users.rest.dto.response.UserDataResponse;
import com.gregory.api.users.rest.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface IUserController {

    @GetMapping
    ResponseEntity<UserDataResponse> getUsers(@RequestParam(value = "user_id", required = false) String userId,
                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size);

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createUser(@RequestBody @Valid UserRequest request);

    @PatchMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponse> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid UserUpdateRequest request);

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id);
}