package com.example.powerliftingresultsapp.parser;

import com.example.powerliftingresultsapp.model.enums.Sex;
import com.example.powerliftingresultsapp.model.enums.UniversityType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParsedAthleteRecord {

    private int place;

    @Builder.Default
    private int placeInUniversityType = 0;

    private String firstName;
    private String lastName;
    private String university;

    private double bodyWeight;
    private String weightCategory;

    private double squat;
    private double benchPress;
    private double deadlift;

    private double total;
    private double ipfPoints;

    private UniversityType universityType;
    private Sex sex;
}
