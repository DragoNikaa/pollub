package com.example.a2_trwale_przechowywanie_danych;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PhoneDao {
    @Insert
    void insert(Phone phone);

    @Delete
    void delete(Phone phone);

    @Query("SELECT * FROM phone ORDER BY model")
    LiveData<List<Phone>> getAllAlphabetized();

    @Query("DELETE FROM phone")
    void deleteAll();
}