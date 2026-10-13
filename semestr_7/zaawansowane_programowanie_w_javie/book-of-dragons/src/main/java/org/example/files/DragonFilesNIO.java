package org.example.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class DragonFilesNIO {
    private static final String FILENAME = "species.txt";

    public static void main() {
        writeSpecies();
        System.out.println();

        readSpecies();
        readAndProcessSpecies();
    }

    private static void writeSpecies() {
        List<String> species = List.of(
                "Night Fury",
                "Deadly Nadder",
                "Monstrous Nightmare",
                "Gronckle",
                "Hideous Zippleback"
        );

        try {
            Files.write(Path.of(FILENAME), species);
            System.out.println("Dragon species were successfully saved to the file '" + FILENAME + "'.");
        } catch (IOException e) {
            System.err.println("Could not write species: " + e.getMessage());
        }
    }

    private static void readSpecies() {
        System.out.println("Contents of the file '" + FILENAME + "':");

        try {
            String species = Files.readString(Path.of(FILENAME));
            System.out.println(species);
        } catch (IOException e) {
            System.err.println("Could not read species: " + e.getMessage());
        }
    }

    private static void readAndProcessSpecies() {
        System.out.println("Two-word dragon species sorted by length, from the file '" + FILENAME + "':");

        try {
            List<String> species = Files
                    .readAllLines(Path.of(FILENAME))
                    .stream()
                    .filter(speciesName -> speciesName.trim().split("\\s+").length == 2)
                    .sorted(Comparator.comparingInt(String::length))
                    .toList();

            for (int i = 0; i < species.size(); i++) {
                System.out.println(i + 1 + ". " + species.get(i));
            }
        } catch (IOException e) {
            System.err.println("Could not read species: " + e.getMessage());
        }
    }
}