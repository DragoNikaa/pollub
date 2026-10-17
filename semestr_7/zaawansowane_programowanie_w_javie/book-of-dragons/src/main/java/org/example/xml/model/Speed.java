package org.example.xml.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

public class Speed {
    @XmlValue
    private double value;

    @XmlAttribute
    private String unit = "km/h";

    private Speed() {
    }

    public Speed(double value) {
        this.value = value;
    }

    public Speed(double value, String unit) {
        this(value);
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "'" + value + " " + unit + "'";
    }
}