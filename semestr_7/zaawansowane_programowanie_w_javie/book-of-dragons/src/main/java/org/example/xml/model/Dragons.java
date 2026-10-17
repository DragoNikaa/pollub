package org.example.xml.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class Dragons {
    @XmlElement(name = "dragon")
    private List<Dragon> dragons;

    private Dragons() {
    }

    public Dragons(List<Dragon> dragons) {
        this.dragons = dragons;
    }

    public List<Dragon> getList() {
        return dragons;
    }
}