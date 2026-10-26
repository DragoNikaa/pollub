package org.example.spring.rider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rider {
    private Integer id;
    private Boolean active;
    private String firstName;
    private String lastName;
    private String village;
    private Integer age;
    private String favoriteFood;
}