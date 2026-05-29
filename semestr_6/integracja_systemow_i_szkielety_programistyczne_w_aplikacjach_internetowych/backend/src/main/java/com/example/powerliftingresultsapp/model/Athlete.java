package com.example.powerliftingresultsapp.model;

import com.example.powerliftingresultsapp.model.enums.Sex;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document(collection = "athletes")
@CompoundIndex(def = "{'firstName':1,'lastName':1}", unique = true)
public class Athlete {

    @Id
    private ObjectId id;

    private String firstName;
    private String lastName;

    private Sex sex;

    private Set<String> university = new HashSet<>();
    private List<AthleteResult> results = new ArrayList<>();
}
