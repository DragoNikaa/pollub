package com.example.powerliftingresultsapp.model;

import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.model.enums.Sex;
import com.example.powerliftingresultsapp.model.enums.UniversityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "summary_results")
@CompoundIndex(def = "{'year':1,'competitionLevel':1,'sex':1}")
public class SummaryResult {

    @Id
    private ObjectId id;

    private int year;
    private CompetitionLevel competitionLevel;
    private Sex sex;
    private String weightCategory;
    private UniversityType universityType;

    private int numberOfAthletes;

    private double avgSquat;
    private double avgBenchPress;
    private double avgDeadlift;

    private double avgTotal;
    private double avgIpfPoints;
}
