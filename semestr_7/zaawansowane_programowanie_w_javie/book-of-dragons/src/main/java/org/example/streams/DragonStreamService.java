package org.example.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DragonStreamService {
    public static void main() {
        findFurySpecies();
        System.out.println();

        countNames();
        System.out.println();

        normalizeNames();
    }

    private static void findFurySpecies() {
        Stream<String> species = Stream.of(
                "Night Fury",
                "Deadly Nadder",
                "Monstrous Nightmare",
                "Gronckle",
                "Hideous Zippleback",
                "Light Fury",
                "Skrill"
        );
        Stream<String> furies = species.filter(speciesName -> speciesName.contains("Fury"));

        System.out.println("Dragon species containing 'Fury':");
        furies.forEach(fury -> System.out.println("- " + fury));
    }

    private static void countNames() {
        Stream<String> names = Stream.of(
                "Toothless",
                "Stormfly",
                "Hookfang",
                "Toothless",
                "Meatlug",
                "Stormfly",
                "Stormfly",
                "Toothless",
                "Stormfly"
        );
        Map<String, Integer> counts = new HashMap<>();
        names.forEach(name -> counts.put(name, counts.getOrDefault(name, 0) + 1));

        System.out.println("Dragon names with counts:");
        counts.forEach((name, count) -> System.out.println("- " + name + ": " + count));
    }

    private static void normalizeNames() {
        Stream<String> rawNames = Stream.of(
                " tOOthleSs ",
                "  STorMfly",
                "   hookFAng",
                "mEATluG ",
                " bArF   & belCH  "
        );
        Stream<String> normalizedNames = rawNames.map(DragonStreamService::normalizeName);

        System.out.println("Normalized dragon names:");
        normalizedNames.forEach(name -> System.out.println("- " + name));
    }

    private static String normalizeName(String rawName) {
        return Arrays
                .stream(rawName.trim().split("\\s+"))
                .map(word -> Character.toTitleCase(word.charAt(0)) + word.toLowerCase().substring(1))
                .collect(Collectors.joining(" "));
    }
}