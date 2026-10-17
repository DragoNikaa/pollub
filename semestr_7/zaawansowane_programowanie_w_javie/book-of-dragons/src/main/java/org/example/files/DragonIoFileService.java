package org.example.files;

import java.io.*;
import java.util.List;

public class DragonIoFileService {
    private static final String FILE_PATH = "output/names.txt";

    private static final List<String> NAMES = List.of("Toothless", "Stormfly", "Hookfang", "Meatlug", "Barf & Belch");

    public static void main() {
        writeNames();
        System.out.println();

        readNames();
    }

    private static void writeNames() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));

            for (int i = 0; i < NAMES.size(); i++) {
                writer.write(i + 1 + ". " + NAMES.get(i));
                writer.newLine();
            }
            writer.close();

            System.out.println("Dragon names were successfully saved to the file '" + FILE_PATH + "'.");
        } catch (IOException e) {
            System.err.println("Could not write names: " + e.getMessage());
        }
    }

    private static void readNames() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;

            System.out.println("Contents of the file '" + FILE_PATH + "':");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read names: " + e.getMessage());
        }
    }
}