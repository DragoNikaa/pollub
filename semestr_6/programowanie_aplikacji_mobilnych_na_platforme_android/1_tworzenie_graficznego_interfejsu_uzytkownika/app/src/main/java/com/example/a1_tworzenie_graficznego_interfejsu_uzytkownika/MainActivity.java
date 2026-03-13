package com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.function.Predicate;

public class MainActivity extends AppCompatActivity {
    private static final int TOTAL_EDIT_TEXTS = 3;
    private static final Predicate<String> FIRST_NAME_VALIDATOR = text -> text.matches("^[A-ZŁŻ][a-ząćęłńóśźż]{1,25}$");
    private static final Predicate<String> LAST_NAME_VALIDATOR = text -> text.matches("^[A-ZŁŚŻ][a-ząćęłńóśźż]{1,25}(?:-[A-ZŁŚŻ][a-ząćęłńóśźż]{1,25})?$");
    private static final Predicate<String> GRADE_NUMBER_VALIDATOR = text -> {
        if (text.isEmpty()) return false;
        int value = Integer.parseInt(text);
        return value >= 5 && value <= 15;
    };

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText gradeNumberEditText;
    private Button gradesButton;

    private final HashSet<EditText> validEditTexts = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setupEdgeToEdge();

        setupToolbar();
        setupEditTexts();
        setupGradesButton();
    }

    private void setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void setupEditTexts() {
        firstNameEditText = findViewById(R.id.edit_first_name);
        setupEditText(firstNameEditText, FIRST_NAME_VALIDATOR, R.string.first_name_error_required, R.string.first_name_error_invalid);

        lastNameEditText = findViewById(R.id.edit_last_name);
        setupEditText(lastNameEditText, LAST_NAME_VALIDATOR, R.string.last_name_error_required, R.string.last_name_error_invalid);

        gradeNumberEditText = findViewById(R.id.edit_grade_number);
        setupEditText(gradeNumberEditText, GRADE_NUMBER_VALIDATOR, R.string.grade_number_error_required, R.string.grade_number_error_invalid);
    }

    private void setupEditText(EditText editText, Predicate<String> validator, int requiredErrorMessage, int invalidErrorMessage) {
        editText.setOnFocusChangeListener((view, hasFocus) -> onFocusChange(hasFocus, editText, validator, requiredErrorMessage, invalidErrorMessage));
        editText.addTextChangedListener(createTextWatcher(editText, validator));
        updateValidEditTexts(editText, editText.getText().toString(), validator);
    }

    private void setupGradesButton() {
        gradesButton = findViewById(R.id.button_grades);
        updateGradesButtonVisibility();
    }

    private void onFocusChange(boolean hasFocus, EditText editText, Predicate<String> validator, int requiredErrorMessage, int invalidErrorMessage) {
        if (hasFocus) return;

        String text = editText.getText().toString();
        if (!validator.test(text)) {
            showEditTextError(editText, text, requiredErrorMessage, invalidErrorMessage);
        }
    }

    private void showEditTextError(EditText editText, String text, int requiredErrorMessage, int invalidErrorMessage) {
        String errorMessage = getString(text.isEmpty() ? requiredErrorMessage : invalidErrorMessage);
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
        gradesButton.setVisibility(validEditTexts.size() == TOTAL_EDIT_TEXTS ? View.VISIBLE : View.INVISIBLE);
    }
}