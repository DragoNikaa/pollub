package com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika.databinding.ActivityMainConstraintBinding;

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

    private ActivityMainConstraintBinding binding;
    private final HashSet<EditText> validEditTexts = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViewBinding();
        setupEdgeToEdge();
        setupToolbar();
        setupEditTexts();
    }

    private void setupViewBinding() {
        binding = ActivityMainConstraintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
        editText.setOnFocusChangeListener((v, hasFocus) -> onFocusChange(hasFocus, editText, validator, requiredErrorMessage, invalidErrorMessage));
        editText.addTextChangedListener(createTextWatcher(editText, validator));
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
        binding.buttonGrades.setVisibility(validEditTexts.size() == TOTAL_EDIT_TEXTS ? View.VISIBLE : View.INVISIBLE);
    }
}