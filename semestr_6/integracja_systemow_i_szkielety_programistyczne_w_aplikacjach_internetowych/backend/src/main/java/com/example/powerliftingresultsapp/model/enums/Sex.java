package com.example.powerliftingresultsapp.model.enums;

import java.util.Arrays;

public enum Sex {
    MALE, FEMALE;

    public static Sex fromString(String s) {
        try {
            return valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid sex: '" + s + "'. Allowed values: " + Arrays.toString(values()) + "."
            );
        }
    }
}
