package com.gregory.api.users.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_INVALID;
import static com.gregory.api.users.domain.message.CommonsMessage.FIELD_MANDATORY;

@Data
@Builder
public class UserRequest {

    @NotNull(message = FIELD_MANDATORY)
    @NotBlank(message = FIELD_MANDATORY)
    private String name;

    @NotNull(message = FIELD_MANDATORY)
    @NotBlank(message = FIELD_MANDATORY)
    @Email(message = EMAIL_INVALID)
    private String email;

    @NotNull(message = FIELD_MANDATORY)
    @NotBlank(message = FIELD_MANDATORY)
    private String password;

    @NotNull(message = FIELD_MANDATORY)
    @NotBlank(message = FIELD_MANDATORY)
    private String exchange;
}