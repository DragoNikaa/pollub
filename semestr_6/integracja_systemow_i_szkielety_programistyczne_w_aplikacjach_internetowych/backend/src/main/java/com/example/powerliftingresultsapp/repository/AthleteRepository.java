package com.example.powerliftingresultsapp.repository;

import com.example.powerliftingresultsapp.model.Athlete;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AthleteRepository extends MongoRepository<Athlete, ObjectId> {

    Optional<Athlete> findByFirstNameAndLastName(String firstName, String lastName);
}
