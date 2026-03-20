package com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika.databinding.ActivityGradesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GradesActivity extends BaseActivity<ActivityGradesBinding> {
    public static final String AVERAGE_KEY = "average";

    private static final String GRADE_VALUE_KEY = "grade%dValue";

    private int gradeNumber;
    private final List<Grade> grades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityGradesBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setupToolbar();
        setupGradesForm();
        setupAverageButton();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finishActivity(RESULT_CANCELED, null));
    }

    private void setupGradesForm() {
        gradeNumber = getIntent().getIntExtra(MainActivity.GRADE_NUMBER_KEY, 0);
        String[] subjects = getResources().getStringArray(R.array.subjects);
        for (int i = 0; i < gradeNumber; i++) grades.add(new Grade(subjects[i]));

        setupGradeAdapter();
    }

    private void setupGradeAdapter() {
        binding.recyclerGrades.setAdapter(new GradeAdapter(this, grades));
        binding.recyclerGrades.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupAverageButton() {
        binding.buttonAverage.setOnClickListener(v -> onAverageButtonClick());
    }

    private void onAverageButtonClick() {
        if (areAllGradesSelected()) finishActivity(RESULT_OK, createMainIntent());
        else Toast.makeText(this, R.string.grades_error_required, Toast.LENGTH_SHORT).show();
    }

    private boolean areAllGradesSelected() {
        for (Grade grade : grades) if (!grade.isSet()) return false;
        return true;
    }

    private Intent createMainIntent() {
        Intent intent = new Intent();
        intent.putExtra(AVERAGE_KEY, calculateAverage());
        return intent;
    }

    private double calculateAverage() {
        double sum = 0;
        for (Grade grade : grades) sum += grade.getValue();
        return sum / gradeNumber;
    }

    private void finishActivity(int resultCode, Intent intent) {
        setResult(resultCode, intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        saveGrades(outState);
        super.onSaveInstanceState(outState);
    }

    private void saveGrades(@NonNull Bundle outState) {
        for (int i = 0; i < gradeNumber; i++) {
            outState.putDouble(String.format(Locale.US, GRADE_VALUE_KEY, i), grades.get(i).getValue());
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreGrades(savedInstanceState);
    }

    private void restoreGrades(@NonNull Bundle savedInstanceState) {
        for (int i = 0; i < gradeNumber; i++) {
            double gradeValue = savedInstanceState.getDouble(String.format(Locale.US, GRADE_VALUE_KEY, i));
            grades.get(i).setValue(gradeValue);
        }
    }
}