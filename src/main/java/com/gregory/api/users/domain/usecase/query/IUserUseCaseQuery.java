package com.gregory.api.users.domain.usecase.query;

import com.gregory.api.users.rest.dto.response.UserResponse;
import com.gregory.api.users.rest.dto.response.UsersResponse;

import java.util.List;

public interface IUserUseCaseQuery {

    UsersResponse getUsers(int page, int size);
}