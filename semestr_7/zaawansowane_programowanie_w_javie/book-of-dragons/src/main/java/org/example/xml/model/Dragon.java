package org.example.xml.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Dragon {
    private static int nextId = 1;

    @XmlAttribute
    private int id;

    @XmlElement
    private String name;

    @XmlElement
    private String species;

    @XmlElement
    private boolean isFlying;

    @XmlElement
    private double firePower;

    @XmlElement
    private Speed maxSpeed;

    @XmlElement
    private Rarity rarity;

    private Dragon() {
        id = nextId++;
    }

    public Dragon(String name, String species, boolean isFlying, double firePower, Speed maxSpeed, Rarity rarity) {
        this();

        this.name = name;
        this.species = species;
        this.isFlying = isFlying;
        this.firePower = firePower;
        this.maxSpeed = maxSpeed;
        this.rarity = rarity;
    }

    @Override
    public String toString() {
        return "Dragon{" + "id=" + id + ", name='" + name + '\'' + ", species='" + species + '\'' + ", isFlying=" +
                isFlying + ", firePower=" + firePower + ", maxSpeed=" + maxSpeed + ", rarity=" + rarity + '}';
    }
}