package com.example.powerliftingresultsapp.dto.response;

import com.example.powerliftingresultsapp.model.enums.UniversityType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record SummaryResultFiltersResponse(

        @Schema(example = "[\"83\", \"74\", \"120\", \"120+\"]")
        Set<String> weightCategories,

        Set<UniversityType> universityTypes
) {
}
