package com.example.powerliftingresultsapp.service;

import com.example.powerliftingresultsapp.model.SummaryResult;
import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.model.enums.Sex;
import com.example.powerliftingresultsapp.model.enums.UniversityType;
import com.example.powerliftingresultsapp.parser.ParsedAthleteRecord;
import com.example.powerliftingresultsapp.repository.SummaryResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummaryResultService {

    private final SummaryResultRepository summaryResultRepository;

    public void upsert(List<ParsedAthleteRecord> records, int year, CompetitionLevel competitionLevel, Sex sex) {
        summaryResultRepository.deleteByYearAndCompetitionLevelAndSex(year, competitionLevel, sex);
        groupAndCreateSummaries(records, year, competitionLevel, sex);
    }

    private void groupAndCreateSummaries(List<ParsedAthleteRecord> records, int year,
                                         CompetitionLevel competitionLevel, Sex sex) {
        records.stream()
                .collect(Collectors.groupingBy(r -> r.getWeightCategory() + "|" + r.getUniversityType().name()))
                .values()
                .forEach(group -> createAndSaveSummary(group, year, competitionLevel, sex));
    }

    private void createAndSaveSummary(List<ParsedAthleteRecord> records, int year,
                                      CompetitionLevel competitionLevel, Sex sex) {
        ParsedAthleteRecord sampleRecord = records.getFirst();
        summaryResultRepository.save(buildSummary(
                year, competitionLevel, sex, sampleRecord.getWeightCategory(), sampleRecord.getUniversityType(), records)
        );
    }

    private SummaryResult buildSummary(int year, CompetitionLevel competitionLevel, Sex sex, String weightCategory,
                                       UniversityType universityType, List<ParsedAthleteRecord> records) {
        return SummaryResult.builder()
                .year(year)
                .competitionLevel(competitionLevel)
                .sex(sex)
                .weightCategory(weightCategory)
                .universityType(universityType)
                .numberOfAthletes(records.size())
                .avgSquat(avg(records, ParsedAthleteRecord::getSquat))
                .avgBenchPress(avg(records, ParsedAthleteRecord::getBenchPress))
                .avgDeadlift(avg(records, ParsedAthleteRecord::getDeadlift))
                .avgTotal(avg(records, ParsedAthleteRecord::getTotal))
                .avgIpfPoints(avg(records, ParsedAthleteRecord::getIpfPoints))
                .build();
    }

    private double avg(List<ParsedAthleteRecord> records, ToDoubleFunction<ParsedAthleteRecord> getter) {
        return records.stream()
                .mapToDouble(getter)
                .average()
                .orElse(0.0);
    }
}
