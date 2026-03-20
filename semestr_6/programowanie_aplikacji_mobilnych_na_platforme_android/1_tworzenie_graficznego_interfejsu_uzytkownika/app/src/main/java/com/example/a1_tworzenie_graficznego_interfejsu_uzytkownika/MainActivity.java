package com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika.databinding.ActivityMainConstraintBinding;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class MainActivity extends BaseActivity<ActivityMainConstraintBinding> {
    public static final String GRADE_NUMBER_KEY = "gradeNumber";

    private static final int TOTAL_EDIT_TEXTS = 3;
    private static final double MIN_PASS_AVERAGE = 3.0;

    private static final Predicate<String> FIRST_NAME_VALIDATOR = text -> text.matches("^[A-ZŁŻ][a-ząćęłńóśźż]{1,25}$");
    private static final Predicate<String> LAST_NAME_VALIDATOR = text -> text.matches("^[A-ZĆŁÓŚŹŻ][a-ząćęłńóśźż]{1,25}(?:-[A-ZĆŁÓŚŹŻ][a-ząćęłńóśźż]{1,25})?$");
    private static final Predicate<String> GRADE_NUMBER_VALIDATOR = text -> {
        if (text.isEmpty()) return false;
        int value = Integer.parseInt(text);
        return value >= 5 && value <= 15;
    };

    private ActivityResultLauncher<Intent> gradesActivityResultLauncher;
    private final Set<EditText> validEditTexts = new HashSet<>();
    private double average = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainConstraintBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setupGradesActivityResultLauncher();
        setupToolbar();
        setupEditTexts();
        setupGradesButton();
    }

    private void setupGradesActivityResultLauncher() {
        gradesActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onGradesActivityResult);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
    }

    private void setupEditTexts() {
        setupEditText(binding.editFirstName, FIRST_NAME_VALIDATOR, R.string.first_name_error_required, R.string.first_name_error_invalid);
        setupEditText(binding.editLastName, LAST_NAME_VALIDATOR, R.string.last_name_error_required, R.string.last_name_error_invalid);
        setupEditText(binding.editGradeNumber, GRADE_NUMBER_VALIDATOR, R.string.grade_number_error_required, R.string.grade_number_error_invalid);
    }

    private void setupEditText(EditText editText, Predicate<String> validator, int requiredErrorMessage, int invalidErrorMessage) {
        editText.setOnFocusChangeListener((v, hasFocus) -> onEditTextFocusChange(hasFocus, editText, validator, requiredErrorMessage, invalidErrorMessage));
        editText.addTextChangedListener(createTextWatcher(editText, validator));
    }

    private void onEditTextFocusChange(boolean hasFocus, EditText editText, Predicate<String> validator, int requiredErrorMessage, int invalidErrorMessage) {
        if (hasFocus) return;

        String text = editText.getText().toString();
        if (!validator.test(text)) {
            showEditTextError(editText, getString(text.isEmpty() ? requiredErrorMessage : invalidErrorMessage));
        }
    }

    private void showEditTextError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private TextWatcher createTextWatcher(EditText editText, Predicate<String> validator) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateValidEditTexts(editText, s.toString(), validator);
                updateGradesButtonVisibility();
            }
        };
    }

    private void updateValidEditTexts(EditText editText, String text, Predicate<String> validator) {
        if (validator.test(text)) validEditTexts.add(editText);
        else validEditTexts.remove(editText);
    }

    private void updateGradesButtonVisibility() {
        binding.buttonGrades.setVisibility(validEditTexts.size() == TOTAL_EDIT_TEXTS ? View.VISIBLE : View.GONE);
    }

    private void setupGradesButton() {
        binding.buttonGrades.setOnClickListener(v -> gradesActivityResultLauncher.launch(createGradesIntent()));
    }

    private Intent createGradesIntent() {
        int gradeNumber = Integer.parseInt(binding.editGradeNumber.getText().toString());
        Intent intent = new Intent(this, GradesActivity.class);
        intent.putExtra(GRADE_NUMBER_KEY, gradeNumber);
        return intent;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putDouble(GradesActivity.AVERAGE_KEY, average);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        average = savedInstanceState.getDouble(GradesActivity.AVERAGE_KEY);
        displayAverageResult();
    }

    private void onGradesActivityResult(ActivityResult result) {
        if (result.getResultCode() != RESULT_OK || result.getData() == null) return;

        average = result.getData().getDoubleExtra(GradesActivity.AVERAGE_KEY, 0);
        displayAverageResult();
    }

    private void displayAverageResult() {
        if (average == 0) return;
        setupResultText();
        setupExitButton();
    }

    private void setupResultText() {
        binding.textAverage.setText(getString(R.string.average_result_label, average));
        binding.textAverage.setVisibility(View.VISIBLE);
    }

    private void setupExitButton() {
        int label, toastMessage;
        if (average >= MIN_PASS_AVERAGE) {
            label = R.string.average_result_success_label;
            toastMessage = R.string.average_result_success_toast;
        } else {
            label = R.string.average_result_fail_label;
            toastMessage = R.string.average_result_fail_toast;
        }
        binding.buttonGrades.setText(label);
        binding.buttonGrades.setOnClickListener(v -> onExitButtonClick(toastMessage));
    }

    private void onExitButtonClick(int toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        finish();
    }
}