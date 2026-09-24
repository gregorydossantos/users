package com.gregory.api.users.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_INVALID;
import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_REGEX;

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
    private int exchangeCode;
}
