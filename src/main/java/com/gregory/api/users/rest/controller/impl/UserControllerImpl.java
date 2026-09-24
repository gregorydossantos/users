package com.gregory.api.users.rest.controller.impl;

import com.gregory.api.users.rest.controller.IUserController;
import com.gregory.api.users.rest.dto.request.UserRequest;
import com.gregory.api.users.rest.dto.request.UserUpdateRequest;
import com.gregory.api.users.rest.dto.response.UserDataResponse;
import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.services.IUserService;
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

import java.util.Objects;
import java.util.UUID;

import static com.gregory.api.users.rest.constants.ConstantsIndexes.BAD_REQUEST;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.DELETE_METHOD;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.GET_METHOD;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.INTERNAL_SERVER_ERROR;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.PATCH_METHOD;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.POST_METHOD;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.RESPONSE_CODE_200;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.RESPONSE_CODE_201;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.RESPONSE_CODE_400;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.RESPONSE_CODE_404;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.RESPONSE_CODE_500;
import static com.gregory.api.users.rest.constants.ConstantsIndexes.USER_NOT_FOUND;
import static com.gregory.api.users.rest.path.Routes.PATH_USERS;

@RestController
@AllArgsConstructor
@Tag(name = "User Controller")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = PATH_USERS, produces = {"application/json"})
public class UserControllerImpl implements IUserController {


    IUserService userService;

    @Operation(summary = "Get a list (or passing the param user_id return one User) of Users", method = GET_METHOD)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_200, description = "Return a list of users or one user by user_id param"),
            @ApiResponse(responseCode = RESPONSE_CODE_400, description = BAD_REQUEST),
            @ApiResponse(responseCode = RESPONSE_CODE_500, description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<UserDataResponse> getUsers(String userId, int page, int size) {
        if (Objects.nonNull(userId)) {
            return ResponseEntity.ok().body(userService.findByUserId(userId));
        }
        var response = userService.getUsers(page, size);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Create a user", method = POST_METHOD)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_201, description = "Return HTTP status CREATED"),
            @ApiResponse(responseCode = RESPONSE_CODE_400, description = BAD_REQUEST),
            @ApiResponse(responseCode = RESPONSE_CODE_500, description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<Void> createUser(UserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a user", method = PATCH_METHOD)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_200, description = "Return a user update"),
            @ApiResponse(responseCode = RESPONSE_CODE_400, description = BAD_REQUEST),
            @ApiResponse(responseCode = RESPONSE_CODE_404, description = USER_NOT_FOUND),
            @ApiResponse(responseCode = RESPONSE_CODE_500, description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<UserResponse> updateUser(UUID id, UserUpdateRequest request) {
        var response = userService.updateUser(id, request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete a user", method = DELETE_METHOD)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_200, description = "Delete a user"),
            @ApiResponse(responseCode = RESPONSE_CODE_400, description = BAD_REQUEST),
            @ApiResponse(responseCode = RESPONSE_CODE_404, description = USER_NOT_FOUND),
            @ApiResponse(responseCode = RESPONSE_CODE_500, description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}