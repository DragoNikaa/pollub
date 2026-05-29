package com.example.powerliftingresultsapp.model;

import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.model.enums.Sex;
import com.example.powerliftingresultsapp.model.enums.UniversityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AthleteResult {

    private int year;
    private CompetitionLevel competitionLevel;
    private Sex sex;

    private double bodyWeight;
    private String weightCategory;

    private int place;
    private UniversityType universityType;
    private int placeInUniversityType;

    private double squat;
    private double benchPress;
    private double deadlift;

    private double total;
    private double ipfPoints;
}
