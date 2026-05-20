package com.example.a2_trwale_przechowywanie_danych;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PhoneDao {
    @Insert
    void insert(Phone phone);

    @Update
    void update(Phone phone);

    @Delete
    void delete(Phone phone);

    @Query("SELECT * FROM phone ORDER BY model")
    LiveData<List<Phone>> getAllAlphabetized();

    @Query("SELECT COUNT(*) FROM phone")
    int getCount();

    @Query("DELETE FROM phone")
    void deleteAll();
}