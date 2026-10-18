package org.example.lombok.dto;

import lombok.NonNull;

public record LocationDto(
        @NonNull String name,
        @NonNull String type,
        Double hoursFromBerk,
        String description
) {
}