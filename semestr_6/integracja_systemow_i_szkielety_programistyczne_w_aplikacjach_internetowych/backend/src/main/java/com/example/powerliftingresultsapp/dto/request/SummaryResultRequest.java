package com.example.powerliftingresultsapp.dto.request;

import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.model.enums.Sex;
import com.example.powerliftingresultsapp.model.enums.UniversityType;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record SummaryResultRequest(

        @NotNull
        CompetitionLevel competitionLevel,

        @NotNull
        Set<Sex> sexes,

        @NotNull
        Set<String> weightCategories,

        @NotNull
        Set<UniversityType> universityTypes
) {
}
