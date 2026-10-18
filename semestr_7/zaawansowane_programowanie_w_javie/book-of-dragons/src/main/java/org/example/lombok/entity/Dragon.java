package org.example.lombok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Dragon {
    private @NonNull String name;
    private @NonNull String species;
    private @NonNull String color;
    private LocalDate birthDate;
    private boolean isFlying = true;

    public Dragon(String name, String species, String color, LocalDate birthDate) {
        this(name, species, color);
        this.birthDate = birthDate;
    }

    public Dragon(String name, String species, String color, boolean isFlying) {
        this(name, species, color);
        this.isFlying = isFlying;
    }
}