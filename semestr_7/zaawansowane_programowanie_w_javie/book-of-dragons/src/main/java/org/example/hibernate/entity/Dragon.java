package org.example.hibernate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "dragons")
@Data
@NoArgsConstructor
public class Dragon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String species;

    private String color;

    private LocalDate birthDate;

    @ColumnDefault("true")
    private Boolean flying;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Rider rider;
}