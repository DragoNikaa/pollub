package com.example.powerliftingresultsapp.model.enums;

import java.util.Arrays;

public enum UniversityType {
    UME, UNI, USP, UTE, UWF, WSN;

    public static UniversityType fromString(String s) {
        try {
            return valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid university type: '" + s + "'. Allowed values: " + Arrays.toString(values()) + "."
            );
        }
    }
}
