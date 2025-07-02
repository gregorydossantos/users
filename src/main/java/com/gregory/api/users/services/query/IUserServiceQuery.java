package com.gregory.api.users.services.query;

import com.gregory.api.users.rest.dto.response.UsersResponse;

public interface IUserServiceQuery {

    UsersResponse getUsers(int page, int size);
}