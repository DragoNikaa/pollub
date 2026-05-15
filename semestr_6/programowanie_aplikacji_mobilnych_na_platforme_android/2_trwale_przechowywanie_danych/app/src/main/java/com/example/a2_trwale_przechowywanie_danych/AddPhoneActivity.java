package com.example.a2_trwale_przechowywanie_danych;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.a2_trwale_przechowywanie_danych.databinding.ActivityAddPhoneBinding;

public class AddPhoneActivity extends BaseActivity<ActivityAddPhoneBinding> {
    public static final String MANUFACTURER_KEY = "manufacturer";
    public static final String MODEL_KEY = "model";
    public static final String ANDROID_VERSION_KEY = "androidVersion";
    public static final String WEBSITE_KEY = "website";

    private static final String URL_REGEX = "^(https?://)?([\\w-]+\\.)+[\\w-]{2,}(/\\S*)?$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAddPhoneBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setupToolbar();
        setupCancelButton();
        setupSaveButton();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finishActivity(RESULT_CANCELED, null));
    }

    private void setupCancelButton() {
        binding.buttonCancel.setOnClickListener(v -> finishActivity(RESULT_CANCELED, null));
    }

    private void setupSaveButton() {
        binding.buttonSave.setOnClickListener(v -> {
            if (validateFields()) finishActivity(RESULT_OK, createMainIntent());
        });
    }

    private boolean validateFields() {
        boolean manufacturerOk = validateManufacturer();
        boolean modelOk = validateModel();
        boolean versionOk = validateAndroidVersion();
        boolean websiteOk = validateWebsite();

        return manufacturerOk && modelOk && versionOk && websiteOk;
    }

    private boolean validateManufacturer() {
        EditText editText = binding.editManufacturer;
        if (!editText.getText().toString().isEmpty()) return true;
        editText.setError(getString(R.string.manufacturer_error_required));
        return false;
    }

    private boolean validateModel() {
        EditText editText = binding.editModel;
        if (!editText.getText().toString().isEmpty()) return true;
        editText.setError(getString(R.string.model_error_required));
        return false;
    }

    private boolean validateAndroidVersion() {
        EditText editText = binding.editAndroidVersion;
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            editText.setError(getString(R.string.android_version_error_required));
            return false;
        }
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            editText.setError(getString(R.string.android_version_error_invalid));
            return false;
        }
        return true;
    }

    private boolean validateWebsite() {
        EditText editText = binding.editWebsite;
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            editText.setError(getString(R.string.website_error_required));
            return false;
        }
        if (!text.matches(URL_REGEX)) {
            editText.setError(getString(R.string.website_error_invalid));
            return false;
        }
        return true;
    }

    private Intent createMainIntent() {
        return new Intent()
                .putExtra(MANUFACTURER_KEY, binding.editManufacturer.getText().toString())
                .putExtra(MODEL_KEY, binding.editModel.getText().toString())
                .putExtra(ANDROID_VERSION_KEY, Integer.parseInt(binding.editAndroidVersion.getText().toString()))
                .putExtra(WEBSITE_KEY, binding.editWebsite.getText().toString());
    }

    private void finishActivity(int resultCode, Intent intent) {
        setResult(resultCode, intent);
        finish();
    }
}