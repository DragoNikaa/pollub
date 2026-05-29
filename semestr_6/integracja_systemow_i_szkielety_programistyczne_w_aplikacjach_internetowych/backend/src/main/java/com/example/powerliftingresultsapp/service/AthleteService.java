package com.example.powerliftingresultsapp.service;

import com.example.powerliftingresultsapp.model.Athlete;
import com.example.powerliftingresultsapp.model.AthleteResult;
import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.parser.ParsedAthleteRecord;
import com.example.powerliftingresultsapp.repository.AthleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AthleteService {

    private final AthleteRepository athleteRepository;

    @Transactional
    public boolean upsert(ParsedAthleteRecord record, int year, CompetitionLevel competitionLevel) {
        Athlete athlete = getAthlete(record);
        athlete.getUniversity().add(record.getUniversity());
        boolean created = upsertResult(record, athlete, year, competitionLevel);
        athleteRepository.save(athlete);
        return created;
    }

    private Athlete getAthlete(ParsedAthleteRecord record) {
        return athleteRepository
                .findByFirstNameAndLastName(record.getFirstName(), record.getLastName())
                .orElseGet(() -> buildAthlete(record));
    }

    private Athlete buildAthlete(ParsedAthleteRecord record) {
        Athlete athlete = new Athlete();
        athlete.setFirstName(record.getFirstName());
        athlete.setLastName(record.getLastName());
        athlete.setSex(record.getSex());
        return athlete;
    }

    private boolean upsertResult(ParsedAthleteRecord record, Athlete athlete,
                                 int year, CompetitionLevel competitionLevel) {
        Optional<AthleteResult> existingResult = findResult(athlete, year, competitionLevel);
        existingResult.ifPresent(r -> athlete.getResults().remove(r));
        athlete.getResults().add(buildResult(record, year, competitionLevel));
        return existingResult.isEmpty();
    }

    private Optional<AthleteResult> findResult(Athlete athlete, int year, CompetitionLevel competitionLevel) {
        return athlete.getResults().stream()
                .filter(r -> r.getYear() == year && r.getCompetitionLevel() == competitionLevel)
                .findFirst();
    }

    private AthleteResult buildResult(ParsedAthleteRecord record, int year, CompetitionLevel competitionLevel) {
        return AthleteResult.builder()
                .year(year)
                .competitionLevel(competitionLevel)
                .sex(record.getSex())
                .bodyWeight(record.getBodyWeight())
                .weightCategory(record.getWeightCategory())
                .place(record.getPlace())
                .universityType(record.getUniversityType())
                .placeInUniversityType(record.getPlaceInUniversityType())
                .squat(record.getSquat())
                .benchPress(record.getBenchPress())
                .deadlift(record.getDeadlift())
                .total(record.getTotal())
                .ipfPoints(record.getIpfPoints())
                .build();
    }
}
