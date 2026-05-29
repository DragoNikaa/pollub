package com.example.powerliftingresultsapp.service;

import com.example.powerliftingresultsapp.dto.response.ResultsImportResponse;
import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.model.enums.Sex;
import com.example.powerliftingresultsapp.parser.NationalResultsParser;
import com.example.powerliftingresultsapp.parser.ParsedAthleteRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultsImportService {

    private record CreatedUpdated(int created, int updated) {
    }

    private final NationalResultsParser nationalResultsParser;
    private final AthleteService athleteService;
    private final SummaryResultService summaryResultService;

    public ResultsImportResponse importFromUrl(URL url, int year, CompetitionLevel competitionLevel, Sex sex)
            throws IOException {
        List<ParsedAthleteRecord> records = nationalResultsParser.parse(extractText(url), sex);
        CreatedUpdated counts = save(records, year, competitionLevel);
        summaryResultService.upsert(records, year, competitionLevel, sex);

        log.info("Import completed for URL '{}' (year={}, competitionLevel={}, sex={}). Imported {} records (created={}, updated={}).",
                url, year, competitionLevel, sex, records.size(), counts.created, counts.updated);
        return new ResultsImportResponse(records.size(), counts.created, counts.updated);
    }

    private String extractText(URL url) throws IOException {
        byte[] fileBytes = fetchFile(url);
        return extractTextFromPdf(fileBytes);
    }

    private byte[] fetchFile(URL url) throws IOException {
        try (InputStream inputStream = url.openStream()) {
            return inputStream.readAllBytes();
        }
    }

    private String extractTextFromPdf(byte[] fileBytes) throws IOException {
        try (PDDocument document = Loader.loadPDF(fileBytes)) {
            return new PDFTextStripper().getText(document);
        }
    }

    private CreatedUpdated save(List<ParsedAthleteRecord> records, int year, CompetitionLevel competitionLevel) {
        int created = 0, updated = 0;
        for (ParsedAthleteRecord record : records) {
            boolean isNew = athleteService.upsert(record, year, competitionLevel);
            if (isNew) created++;
            else updated++;
        }
        return new CreatedUpdated(created, updated);
    }
}
