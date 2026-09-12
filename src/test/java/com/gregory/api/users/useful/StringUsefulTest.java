package com.gregory.api.users.useful;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.gregory.api.users.useful.StringUseful.nonNullOrEmpty;
import static org.junit.jupiter.api.Assertions.*;

class StringUsefulTest {

    @Test
    @DisplayName("USEFUL LAYER ::: Should be validate a STRING")
    void should_Return_True_Or_False_After_Validate_String() {
        assertTrue(nonNullOrEmpty("Test"));
        assertFalse(nonNullOrEmpty(" "));
        assertFalse(nonNullOrEmpty(null));
    }
}