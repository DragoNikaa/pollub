package org.example.lombok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Location {
    private @NonNull String name;
    private @NonNull String type;
    private Double hoursFromBerk;
    private String description;

    public Location(String name, String type, double hoursFromBerk) {
        this(name, type);
        this.hoursFromBerk = hoursFromBerk;
    }

    public Location(String name, String type, String description) {
        this(name, type);
        this.description = description;
    }
}