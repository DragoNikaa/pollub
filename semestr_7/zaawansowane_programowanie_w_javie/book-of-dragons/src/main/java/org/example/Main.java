package org.example;

import org.example.files.DragonIoFileService;
import org.example.files.DragonNioFileService;
import org.example.streams.DragonStreamService;
import org.example.xml.DragonXmlService;

public class Main {
    static void main() {
        xml();
    }

    private static void streamsAndFiles() {
        System.out.println("----- STREAMS -----");
        DragonStreamService.main();

        System.out.println("\n----- FILES: IO -----");
        DragonIoFileService.main();

        System.out.println("\n----- FILES: NIO -----");
        DragonNioFileService.main();
    }

    private static void xml() {
        DragonXmlService.main();
    }
}