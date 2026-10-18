package org.example.lombok.builder;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@ToString
public class DragonBuilder {
    private @NonNull String name;
    private @NonNull String species;
    private @NonNull String color;
    private LocalDate birthDate;

    @Builder.Default
    private boolean isFlying = true;
}