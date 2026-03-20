package com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika;

public class Grade {
    private final String subject;
    private double value;

    public Grade(String subject, double value) {
        this.subject = subject;
        this.value = value;
    }

    public Grade(String subject) {
        this(subject, 0);
    }

    public boolean isSet() {
        return value != 0;
    }

    public String getSubject() {
        return subject;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}