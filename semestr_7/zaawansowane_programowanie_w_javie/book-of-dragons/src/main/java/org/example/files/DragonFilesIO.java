package org.example.files;

import java.io.*;
import java.util.List;

public class DragonFilesIO {
    private static final String FILENAME = "names.txt";

    public static void main() {
        writeNames();
        System.out.println();

        readNames();
    }

    private static void writeNames() {
        List<String> names = List.of("Toothless", "Stormfly", "Hookfang", "Meatlug", "Barf & Belch");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME));

            for (int i = 0; i < names.size(); i++) {
                writer.write(i + 1 + ". " + names.get(i));
                writer.newLine();
            }
            writer.close();

            System.out.println("Dragon names were successfully saved to the file '" + FILENAME + "'.");
        } catch (IOException e) {
            System.err.println("Could not write names: " + e.getMessage());
        }
    }

    private static void readNames() {
        System.out.println("Contents of the file '" + FILENAME + "':");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read names: " + e.getMessage());
        }
    }
}