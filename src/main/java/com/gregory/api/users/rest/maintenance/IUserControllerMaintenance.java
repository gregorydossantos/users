package com.gregory.api.users.rest.maintenance;

import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface IUserControllerMaintenance {

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createUser(@Valid @RequestBody UserRequest request);

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponse> updateUser(@RequestParam("id") UUID id, @RequestBody @Valid UserRequest request);

    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteUser(@RequestParam("id") UUID id);
}