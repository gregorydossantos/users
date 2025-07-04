package com.gregory.api.users.rest.query.impl;

import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.dto.response.UsersResponse;
import com.gregory.api.users.rest.query.IUserControllerQuery;
import com.gregory.api.users.services.query.IUserServiceQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.gregory.api.users.rest.path.Routes.PATH_USERS;

@RestController
@AllArgsConstructor
@Tag(name = "User Controller")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = PATH_USERS, produces = {"application/json"})
public class UserControllerQueryImpl implements IUserControllerQuery {

    IUserServiceQuery userService;

    @Operation(summary = "Get a list of Users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return a list of users"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @Override
    public ResponseEntity<UsersResponse> getUsers(String userId, int page, int size) {
        if (Objects.nonNull(userId)) {
            return ResponseEntity.ok().body(userService.findByUserId(userId));
        }
        var response = userService.getUsers(page, size);
        return ResponseEntity.ok().body(response);
    }
}