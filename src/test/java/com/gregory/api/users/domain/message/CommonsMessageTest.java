package com.gregory.api.users.domain.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.gregory.api.users.domain.message.CommonsMessage.BAD_REQUEST;
import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_ALREADY_REGISTER;
import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_INVALID;
import static com.gregory.api.users.domain.message.CommonsMessage.EMAIL_REGEX;
import static com.gregory.api.users.domain.message.CommonsMessage.ENUMS_VALIDATED;
import static com.gregory.api.users.domain.message.CommonsMessage.FIELD_MANDATORY;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_ALREADY_REGISTER;
import static com.gregory.api.users.domain.message.CommonsMessage.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

class CommonsMessageTest {

    @Test
    @DisplayName("MESSAGE LAYER ::: Testing the commons message")
    void commonsMessageTest() {
        assertThat("User already register!".equalsIgnoreCase(USER_ALREADY_REGISTER)).isTrue();
        assertThat("Invalid email!".equalsIgnoreCase(EMAIL_INVALID)).isTrue();
        assertThat("User not found!".equalsIgnoreCase(USER_NOT_FOUND)).isTrue();
        assertThat("Is a mandatory field!".equalsIgnoreCase(FIELD_MANDATORY)).isTrue();
        assertThat("Bad request!".equalsIgnoreCase(BAD_REQUEST)).isTrue();
        assertThat("Email already register!".equalsIgnoreCase(EMAIL_ALREADY_REGISTER)).isTrue();
        assertThat("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$".equalsIgnoreCase(EMAIL_REGEX)).isTrue();
        assertThat("Codes accepted [0, 1, 2]!".equalsIgnoreCase(ENUMS_VALIDATED)).isTrue();
    }
}