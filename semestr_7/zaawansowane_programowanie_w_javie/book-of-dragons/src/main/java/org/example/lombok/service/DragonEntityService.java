package org.example.lombok.service;

import org.example.lombok.entity.Dragon;
import org.example.lombok.entity.Location;
import org.example.lombok.entity.Rider;

import java.time.LocalDate;

public class DragonEntityService {
    public static void main() {
        showDragons();
        System.out.println();

        showRider();
        System.out.println();

        showLocation();
    }

    private static void showDragons() {
        Dragon toothless = new Dragon("Toothless", "Night Fury", "black");
        System.out.println(toothless);

        toothless.setBirthDate(LocalDate.of(1995, 2, 1));
        System.out.println("Toothless's birth date after update: " + toothless.getBirthDate());

        Dragon speedy = new Dragon("Speedy", "Speed Stinger", "green", LocalDate.of(2015, 7, 21), false);
        System.out.println(speedy);
    }

    private static void showRider() {
        Rider astrid = new Rider("Astrid", "Hofferson", "Berk", 22, "Yaknog");
        System.out.println(astrid);
    }

    private static void showLocation() {
        Location hiddenWorld = new Location("Hidden World", "caverns", 3.0, "Ancestral home of all dragons.");
        System.out.println(hiddenWorld);
    }
}