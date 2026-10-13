package org.example;

import org.example.files.DragonFilesIO;
import org.example.files.DragonFilesNIO;
import org.example.streams.DragonStreams;

public class Main {
    static void main() {
        System.out.println("----- STREAMS -----");
        DragonStreams.main();

        System.out.println("\n----- FILES: IO -----");
        DragonFilesIO.main();

        System.out.println("\n----- FILES: NIO -----");
        DragonFilesNIO.main();
    }
}