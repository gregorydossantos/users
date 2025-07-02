package com.gregory.api.users.services.query.impl;

import com.gregory.api.users.domain.usecase.query.IUserUseCaseQuery;
import com.gregory.api.users.rest.dto.response.UsersResponse;
import com.gregory.api.users.services.query.IUserServiceQuery;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceQueryImpl implements IUserServiceQuery {

    IUserUseCaseQuery userQueryUseCase;

    @Override
    public UsersResponse getUsers(int page, int size) {
        return userQueryUseCase.getUsers(page, size);
    }
}