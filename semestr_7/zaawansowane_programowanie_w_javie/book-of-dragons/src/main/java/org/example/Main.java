package org.example;

import org.example.files.DragonIoFileService;
import org.example.files.DragonNioFileService;
import org.example.lombok.service.DragonBuilderService;
import org.example.lombok.service.DragonDtoService;
import org.example.lombok.service.DragonEntityService;
import org.example.streams.DragonStreamService;
import org.example.xml.DragonXmlService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.example.spring")
public class Main {
    static void main() {
        spring();
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

    private static void lombok() {
        System.out.println("----- ENTITIES -----");
        DragonEntityService.main();

        System.out.println("\n----- BUILDERS -----");
        DragonBuilderService.main();

        System.out.println("\n----- DTO -----");
        DragonDtoService.main();
    }

    private static void spring() {
        SpringApplication.run(Main.class);
    }
}