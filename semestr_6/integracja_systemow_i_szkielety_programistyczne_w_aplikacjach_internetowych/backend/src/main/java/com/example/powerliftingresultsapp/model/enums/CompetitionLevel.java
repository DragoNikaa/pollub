package com.example.powerliftingresultsapp.model.enums;

import java.util.Arrays;

public enum CompetitionLevel {
    NATIONAL, REGIONAL;

    public static CompetitionLevel fromString(String s) {
        try {
            return valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid competition level: '" + s + "'. Allowed values: " + Arrays.toString(values()) + "."
            );
        }
    }
}
