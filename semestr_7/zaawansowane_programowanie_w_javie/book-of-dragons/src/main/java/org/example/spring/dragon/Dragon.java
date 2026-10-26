package org.example.spring.dragon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dragon {
    private Integer id;
    private Integer riderId;
    private String name;
    private String species;
    private String color;
    private LocalDate birthDate;
    private Boolean flying;
}