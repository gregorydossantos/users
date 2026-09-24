package com.gregory.api.users.useful;

public class StringUseful {
    public static boolean nonNullOrEmpty(String value) {
        return value != null && !value.isBlank();
    }
}
