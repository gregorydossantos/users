package com.gregory.api.users.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public class UserDataResponse {

    @JsonProperty("users")
    private List<UserResponse> users;
}
