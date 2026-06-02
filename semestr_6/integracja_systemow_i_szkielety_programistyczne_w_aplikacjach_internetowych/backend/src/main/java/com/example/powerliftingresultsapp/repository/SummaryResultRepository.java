package com.example.powerliftingresultsapp.repository;

import com.example.powerliftingresultsapp.dto.response.SummaryResultResponse;
import com.example.powerliftingresultsapp.model.SummaryResult;
import com.example.powerliftingresultsapp.model.enums.CompetitionLevel;
import com.example.powerliftingresultsapp.model.enums.Sex;
import com.example.powerliftingresultsapp.model.enums.UniversityType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SummaryResultRepository extends MongoRepository<SummaryResult, ObjectId> {

    @Aggregation(pipeline = {
        """
        {
            "$match": {
                "competitionLevel": ?0,
                "sex": { "$in": ?1 },
                "weightCategory": { "$in": ?2 },
                "universityType": { "$in": ?3 }
            }
        }
        """,
        """
        {
            "$group": {
                "_id": "$year",
                "groupNumberOfAthletes": { "$sum": "$numberOfAthletes" },
                "groupSquatSum": { "$sum": "$squatSum" },
                "groupBenchPressSum": { "$sum": "$benchPressSum" },
                "groupDeadliftSum": { "$sum": "$deadliftSum" },
                "groupTotalSum": { "$sum": "$totalSum" },
                "groupIpfPointsSum": { "$sum": "$ipfPointsSum" }
            }
        }
        """,
        """
        {
            "$project": {
                "year": "$_id",
                "numberOfAthletes": "$groupNumberOfAthletes",
                "avgSquat": { "$divide": [ "$groupSquatSum", "$groupNumberOfAthletes" ] },
                "avgBenchPress": { "$divide": [ "$groupBenchPressSum", "$groupNumberOfAthletes" ] },
                "avgDeadlift": { "$divide": [ "$groupDeadliftSum", "$groupNumberOfAthletes" ] },
                "avgTotal": { "$divide": [ "$groupTotalSum", "$groupNumberOfAthletes" ] },
                "avgIpfPoints": { "$divide":[ "$groupIpfPointsSum", "$groupNumberOfAthletes" ] },
                "_id": 0
            }
        }
        """
    })
    List<SummaryResultResponse> findAndGroupByYear(
            CompetitionLevel competitionLevel, Set<Sex> sexes,
            Set<String> weightCategories, Set<UniversityType> universityTypes
    );

    void deleteByYearAndCompetitionLevelAndSex(int year, CompetitionLevel competitionLevel, Sex sex);
}
