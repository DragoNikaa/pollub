package org.example.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.example.xml.model.Dragon;
import org.example.xml.model.Dragons;
import org.example.xml.model.Rarity;
import org.example.xml.model.Speed;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URL;
import java.util.List;

public class DragonXmlService {
    private static final String FILE_PATH = "output/dragons.xml";

    private static final Dragons DRAGONS = new Dragons(List.of(
            new Dragon("Toothless", "Night Fury", true, 95, new Speed(150), Rarity.LEGENDARY),
            new Dragon("Stormfly", "Deadly Nadder", true, 80, new Speed(70, "mph"), Rarity.UNCOMMON),
            new Dragon("Speedy", "Speed Stinger", false, 0, new Speed(105), Rarity.RARE)
    ));

    public static void main() {
        serializeDragons();
        System.out.println();
        deserializeDragons();
    }

    private static void serializeDragons() {
        try {
            Marshaller marshaller = createValidatedMarshaller();
            marshaller.marshal(DRAGONS, new File(FILE_PATH));

            System.out.println("Dragons were successfully serialized to the file '" + FILE_PATH + "'.");
        } catch (JAXBException | SAXException e) {
            System.err.println("Could not serialize dragons: " + e.getCause().getMessage());
        }
    }

    private static void deserializeDragons() {
        try {
            Unmarshaller unmarshaller = createValidatedUnmarshaller();
            Dragons dragons = (Dragons) unmarshaller.unmarshal(new File(FILE_PATH));

            System.out.println("Deserialized dragons from the file '" + FILE_PATH + "':");
            for (Dragon dragon : dragons.getList()) {
                System.out.println("- " + dragon);
            }
        } catch (JAXBException | SAXException e) {
            System.err.println("Could not deserialize dragons: " + e.getCause().getMessage());
        }
    }

    private static Marshaller createValidatedMarshaller() throws JAXBException, SAXException {
        Marshaller marshaller = getContext().createMarshaller();
        marshaller.setSchema(loadSchema());
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    private static Unmarshaller createValidatedUnmarshaller() throws JAXBException, SAXException {
        Unmarshaller unmarshaller = getContext().createUnmarshaller();
        unmarshaller.setSchema(loadSchema());
        return unmarshaller;
    }

    private static JAXBContext getContext() throws JAXBException {
        return JAXBContext.newInstance(Dragons.class);
    }

    private static Schema loadSchema() throws SAXException {
        URL xsdFile = DragonXmlService.class.getResource("/dragons.xsd");
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return factory.newSchema(xsdFile);
    }
}