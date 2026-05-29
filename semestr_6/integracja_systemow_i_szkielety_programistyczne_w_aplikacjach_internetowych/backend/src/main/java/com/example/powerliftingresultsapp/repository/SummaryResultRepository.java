package com.example.powerliftingresultsapp.repository;

import com.example.powerliftingresultsapp.model.SummaryResult;
import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.model.enums.Sex;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryResultRepository extends MongoRepository<SummaryResult, ObjectId> {

    void deleteByYearAndCompetitionLevelAndSex(int year, CompetitionLevel competitionLevel, Sex sex);
}
