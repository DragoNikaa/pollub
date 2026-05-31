package com.example.powerliftingresultsapp.controller;

import com.example.powerliftingresultsapp.dto.request.ResultsImportRequest;
import com.example.powerliftingresultsapp.dto.request.SummaryResultRequest;
import com.example.powerliftingresultsapp.dto.response.ErrorResponse;
import com.example.powerliftingresultsapp.dto.response.ResultsImportResponse;
import com.example.powerliftingresultsapp.dto.response.SummaryResultFiltersResponse;
import com.example.powerliftingresultsapp.dto.response.SummaryResultResponse;
import com.example.powerliftingresultsapp.service.ResultsImportService;
import com.example.powerliftingresultsapp.service.SummaryResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultsController {

    private final ResultsImportService resultsImportService;
    private final SummaryResultService summaryResultService;

    @GetMapping
    public List<SummaryResultResponse> getSummariesByYear(@Valid @ModelAttribute SummaryResultRequest request) {
        return summaryResultService.getByYear(
                request.competitionLevel(), request.sexes(), request.weightCategories(), request.universityTypes()
        );
    }

    @PostMapping
    public ResultsImportResponse importFromUrl(@Valid @RequestBody ResultsImportRequest request) throws IOException {
        return resultsImportService.importFromUrl(
                request.url(), request.year(), request.competitionLevel(), request.sex()
        );
    }

    @GetMapping("/filters")
    public SummaryResultFiltersResponse getFilters() {
        return summaryResultService.getFilters();
    }

    @ExceptionHandler({IOException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleError(Exception e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getClass().getSimpleName(), e.getMessage())
        );
    }
}
