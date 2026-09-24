package com.gregory.api.users.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_INVALID;
import static com.gregory.api.users.domain.message.CommonsMessage.ENUMS_VALIDATED;
import static com.gregory.api.users.domain.message.CommonsMessage.FIELD_MANDATORY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonProperty("exchange_code")
    @Min(value = 0, message = ENUMS_VALIDATED)
    @Max(value = 2, message = ENUMS_VALIDATED)
    private int exchangeCode;
}