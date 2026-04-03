package com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika;

public class Grade {
    private static final double DEFAULT_VALUE = 0;

    private final String subject;
    private double value;

    public Grade(String subject, double value) {
        this.subject = subject;
        this.value = value;
    }

    public Grade(String subject) {
        this(subject, DEFAULT_VALUE);
    }

    public boolean isSet() {
        return value != DEFAULT_VALUE;
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

    public void setDefaultValue() {
        setValue(DEFAULT_VALUE);
    }
}