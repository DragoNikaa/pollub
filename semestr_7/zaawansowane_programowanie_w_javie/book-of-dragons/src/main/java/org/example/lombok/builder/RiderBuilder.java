package org.example.lombok.builder;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
public class RiderBuilder {
    private @NonNull String firstName;
    private @NonNull String lastName;
    private @NonNull String village;
    private @NonNull Integer age;
    private String favoriteFood;
}