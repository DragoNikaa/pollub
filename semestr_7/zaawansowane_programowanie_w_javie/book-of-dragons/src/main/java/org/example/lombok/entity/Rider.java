package org.example.lombok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Rider {
    private @NonNull String firstName;
    private @NonNull String lastName;
    private @NonNull String village;
    private @NonNull Integer age;
    private String favoriteFood;
}