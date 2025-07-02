package com.gregory.api.users.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import static com.gregory.api.users.domain.message.CommonsMessage.FIELD_MANDATORY;
import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_INVALID;
import static com.gregory.api.users.domain.message.CommonsMessage.EXCHANGE_SIZE;
import static com.gregory.api.users.domain.message.CommonsMessage.NAME_SIZE;

@Data
@Builder
public class UserRequest {

    @NotNull(message = FIELD_MANDATORY)
    @NotEmpty(message = FIELD_MANDATORY)
    @Size(max = 10, message = NAME_SIZE)
    private String name;

    @NotNull(message = FIELD_MANDATORY)
    @NotEmpty(message = FIELD_MANDATORY)
    @Email(message = EMAIL_INVALID)
    private String email;

    @NotNull(message = FIELD_MANDATORY)
    @NotEmpty(message = FIELD_MANDATORY)
    private String password;

    @NotNull(message = FIELD_MANDATORY)
    @NotEmpty(message = FIELD_MANDATORY)
    @Size(max = 10, message = EXCHANGE_SIZE)
    private String exchange;
}