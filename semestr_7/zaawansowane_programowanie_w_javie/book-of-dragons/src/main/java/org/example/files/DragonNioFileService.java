package org.example.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class DragonNioFileService {
    private static final String FILE_PATH = "output/species.txt";

    private static final List<String> SPECIES = List.of(
            "Night Fury",
            "Deadly Nadder",
            "Monstrous Nightmare",
            "Gronckle",
            "Hideous Zippleback"
    );

    public static void main() {
        writeSpecies();
        System.out.println();

        readSpecies();
        readAndProcessSpecies();
    }

    private static void writeSpecies() {
        try {
            Files.write(Path.of(FILE_PATH), SPECIES);
            System.out.println("Dragon species were successfully saved to the file '" + FILE_PATH + "'.");
        } catch (IOException e) {
            System.err.println("Could not write species: " + e.getMessage());
        }
    }

    private static void readSpecies() {
        try {
            String species = Files.readString(Path.of(FILE_PATH));
            System.out.println("Contents of the file '" + FILE_PATH + "':\n" + species);
        } catch (IOException e) {
            System.err.println("Could not read species: " + e.getMessage());
        }
    }

    private static void readAndProcessSpecies() {
        try {
            List<String> species = Files
                    .readAllLines(Path.of(FILE_PATH))
                    .stream()
                    .filter(speciesName -> speciesName.strip().split("\\s+").length == 2)
                    .sorted(Comparator.comparingInt(String::length))
                    .toList();

            System.out.println("Two-word dragon species sorted by length, from the file '" + FILE_PATH + "':");
            for (int i = 0; i < species.size(); i++) {
                System.out.println(i + 1 + ". " + species.get(i));
            }
        } catch (IOException e) {
            System.err.println("Could not read species: " + e.getMessage());
        }
    }
}