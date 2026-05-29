package com.example.powerliftingresultsapp.controller;

import com.example.powerliftingresultsapp.dto.request.ResultsImportRequest;
import com.example.powerliftingresultsapp.dto.response.ErrorResponse;
import com.example.powerliftingresultsapp.dto.response.ResultsImportResponse;
import com.example.powerliftingresultsapp.service.ResultsImportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultsController {

    private final ResultsImportService resultsImportService;

    @PostMapping
    public ResultsImportResponse importFromUrl(@Valid @RequestBody ResultsImportRequest request) throws IOException {
        return resultsImportService.importFromUrl(
                request.url(), request.year(), request.competitionLevel(), request.sex()
        );
    }

    @ExceptionHandler({IOException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleError(Exception e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getClass().getSimpleName(), e.getMessage())
        );
    }
}
