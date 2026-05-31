package com.example.powerliftingresultsapp.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ResultsImportResponse(

        @Schema(example = "150")
        int total,

        @Schema(example = "142")
        int created,

        @Schema(example = "8")
        int updated
) {
}
