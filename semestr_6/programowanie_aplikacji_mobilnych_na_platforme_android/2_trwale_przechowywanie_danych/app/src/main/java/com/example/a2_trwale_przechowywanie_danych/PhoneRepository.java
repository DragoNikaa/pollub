package com.example.a2_trwale_przechowywanie_danych;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {
    private PhoneDao phoneDao;
    private LiveData<List<Phone>> phones;

    PhoneRepository(Application application) {
        PhoneRoomDatabase roomDatabase = PhoneRoomDatabase.getDatabase(application);
        phoneDao = roomDatabase.phoneDao();
        phones = phoneDao.getAllAlphabetized();
    }

    void insert(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> phoneDao.insert(phone));
    }

    LiveData<List<Phone>> getAll() {
        return phones;
    }

    void delete(Phone phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> phoneDao.delete(phone));
    }

    void deleteAll() {
        PhoneRoomDatabase.databaseWriteExecutor.execute(() -> phoneDao.deleteAll());
    }
}