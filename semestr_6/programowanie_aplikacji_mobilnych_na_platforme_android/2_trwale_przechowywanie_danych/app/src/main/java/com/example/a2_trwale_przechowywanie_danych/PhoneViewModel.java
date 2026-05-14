package com.example.a2_trwale_przechowywanie_danych;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private final PhoneRepository repository;
    private final LiveData<List<Phone>> phones;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        repository = new PhoneRepository(application);
        phones = repository.getAll();
    }

    public void insert(Phone phone) {
        repository.insert(phone);
    }

    LiveData<List<Phone>> getAll() {
        return phones;
    }

    public void delete(Phone phone) {
        repository.delete(phone);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}