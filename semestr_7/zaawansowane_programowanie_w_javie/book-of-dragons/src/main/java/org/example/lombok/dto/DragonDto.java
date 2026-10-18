package org.example.lombok.dto;

import lombok.NonNull;

import java.time.LocalDate;

public record DragonDto(
        @NonNull String name,
        @NonNull String species,
        @NonNull String color,
        LocalDate birthDate,
        boolean isFlying
) {
}