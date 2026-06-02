package com.example.powerliftingresultsapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SummaryResultResponse(

        @Schema(example = "2026")
        int year,

        @Schema(example = "123")
        int numberOfAthletes,

        @Schema(example = "165.4")
        double avgSquat,

        @Schema(example = "97.8")
        double avgBenchPress,

        @Schema(example = "195.2")
        double avgDeadlift,

        @Schema(example = "458.4")
        double avgTotal,

        @Schema(example = "73.5")
        double avgIpfPoints
) {
}
