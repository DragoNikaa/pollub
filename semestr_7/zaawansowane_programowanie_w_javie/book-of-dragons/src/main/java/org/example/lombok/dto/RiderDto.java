package org.example.lombok.dto;

import lombok.NonNull;

public record RiderDto(
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull String village,
        @NonNull Integer age,
        String favoriteFood
) {
}