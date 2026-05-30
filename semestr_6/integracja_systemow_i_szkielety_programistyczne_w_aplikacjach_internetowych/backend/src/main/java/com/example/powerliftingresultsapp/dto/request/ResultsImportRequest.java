package com.example.powerliftingresultsapp.dto.request;

import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.model.enums.Sex;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.net.URL;

public record ResultsImportRequest(
        @NotNull
        URL url,

        @NotNull
        @Min(2023)
        Integer year,

        @NotNull
        CompetitionLevel competitionLevel,

        Sex sex
) {
}
