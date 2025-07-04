package com.gregory.api.users.rest.maintenance.impl;

import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.maintenance.IUserControllerMaintenance;
import com.gregory.api.users.services.maintenance.IUserServiceMaintenance;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gregory.api.users.rest.path.Routes.PATH_USERS;

@RestController
@AllArgsConstructor
@Tag(name = "User Controller")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = PATH_USERS, produces = {"application/json"})
public class UserControllerMaintenanceImpl implements IUserControllerMaintenance {

    IUserServiceMaintenance userServiceMaintenance;

    @Operation(summary = "Create a user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Return HTTP status CREATED"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @Override
    public ResponseEntity<Void> createUser(UserRequest request) {
        userServiceMaintenance.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a user update"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @Override
    public ResponseEntity<UserResponse> updateUser(String userId, UserRequest request) {
        var response = userServiceMaintenance.updateUser(userId, request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete a user", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a user"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @Override
    public ResponseEntity<Void> deleteUser(String userId) {
        userServiceMaintenance.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}