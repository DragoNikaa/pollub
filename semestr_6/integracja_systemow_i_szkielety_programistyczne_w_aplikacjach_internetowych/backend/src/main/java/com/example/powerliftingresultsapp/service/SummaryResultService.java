package com.example.powerliftingresultsapp.service;

import com.example.powerliftingresultsapp.dto.response.SummaryResultFiltersResponse;
import com.example.powerliftingresultsapp.dto.response.SummaryResultResponse;
import com.example.powerliftingresultsapp.model.SummaryResult;
import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.model.enums.Sex;
import com.example.powerliftingresultsapp.model.enums.UniversityType;
import com.example.powerliftingresultsapp.parser.ParsedAthleteRecord;
import com.example.powerliftingresultsapp.repository.SummaryResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummaryResultService {

    private final SummaryResultRepository summaryResultRepository;
    private final MongoTemplate mongoTemplate;

    public List<SummaryResultResponse> getByYear(CompetitionLevel competitionLevel, Set<Sex> sexes,
                                                 Set<String> weightCategories, Set<UniversityType> universityTypes) {
        return summaryResultRepository.findAndGroupByYear(competitionLevel, sexes, weightCategories, universityTypes);
    }

    public void upsert(List<ParsedAthleteRecord> records, int year, CompetitionLevel competitionLevel, Sex sex) {
        summaryResultRepository.deleteByYearAndCompetitionLevelAndSex(year, competitionLevel, sex);
        groupAndCreateSummaries(records, year, competitionLevel, sex);
    }

    public SummaryResultFiltersResponse getFilters() {
        List<String> weightCategories = mongoTemplate.findDistinct("weightCategory", SummaryResult.class, String.class);

        return new SummaryResultFiltersResponse(
                new HashSet<>(weightCategories),
                EnumSet.allOf(UniversityType.class)
        );
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
                .squatSum(sum(records, ParsedAthleteRecord::getSquat))
                .benchPressSum(sum(records, ParsedAthleteRecord::getBenchPress))
                .deadliftSum(sum(records, ParsedAthleteRecord::getDeadlift))
                .totalSum(sum(records, ParsedAthleteRecord::getTotal))
                .ipfPointsSum(sum(records, ParsedAthleteRecord::getIpfPoints))
                .build();
    }

    private double sum(List<ParsedAthleteRecord> records, ToDoubleFunction<ParsedAthleteRecord> getter) {
        return records.stream()
                .mapToDouble(getter)
                .sum();
    }
}
