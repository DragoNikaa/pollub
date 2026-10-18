package org.example.lombok.builder;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
public class LocationBuilder {
    private @NonNull String name;
    private @NonNull String type;
    private Double hoursFromBerk;
    private String description;
}