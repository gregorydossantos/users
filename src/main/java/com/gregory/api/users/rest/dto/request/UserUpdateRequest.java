package com.gregory.api.users.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_INVALID;
import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_REGEX;
import static com.gregory.api.users.domain.message.CommonsMessage.ENUMS_VALIDATED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    @Email(regexp = EMAIL_REGEX, message = EMAIL_INVALID)
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("exchange_code")
    @Min(value = 0, message = ENUMS_VALIDATED)
    @Max(value = 2, message = ENUMS_VALIDATED)
    private int exchangeCode;
}
