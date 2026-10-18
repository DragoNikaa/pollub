package org.example.lombok.service;

import org.example.lombok.builder.DragonBuilder;
import org.example.lombok.builder.LocationBuilder;
import org.example.lombok.builder.RiderBuilder;

import java.time.LocalDate;

public class DragonBuilderService {
    public static void main() {
        showDragons();
        System.out.println();

        showRider();
        System.out.println();

        showLocation();
    }

    private static void showDragons() {
        DragonBuilder toothless = DragonBuilder
                .builder()
                .name("Toothless")
                .species("Night Fury")
                .color("black")
                .build();
        System.out.println(toothless);

        DragonBuilder speedy = DragonBuilder
                .builder()
                .name("Speedy")
                .species("Speed Stinger")
                .color("green")
                .birthDate(LocalDate.of(2015, 7, 21))
                .isFlying(false)
                .build();
        System.out.println(speedy);
    }

    private static void showRider() {
        RiderBuilder astrid = RiderBuilder
                .builder()
                .firstName("Astrid")
                .lastName("Hofferson")
                .village("Berk")
                .age(22)
                .favoriteFood("Yaknog")
                .build();
        System.out.println(astrid);
    }

    private static void showLocation() {
        LocationBuilder hiddenWorld = LocationBuilder
                .builder()
                .name("Hidden World")
                .type("caverns")
                .hoursFromBerk(3.0)
                .description("Ancestral home of all dragons.")
                .build();
        System.out.println(hiddenWorld);
    }
}