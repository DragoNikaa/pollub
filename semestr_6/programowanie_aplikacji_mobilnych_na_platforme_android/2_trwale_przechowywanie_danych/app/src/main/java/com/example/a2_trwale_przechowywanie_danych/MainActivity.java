package com.example.a2_trwale_przechowywanie_danych;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2_trwale_przechowywanie_danych.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private PhoneAdapter phoneAdapter;
    private PhoneViewModel phoneViewModel;

    private ActivityResultLauncher<Intent> addPhoneActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setupToolbar();
        setupPhoneAdapter();
        setupPhoneViewModel();
        setupAddPhoneActivityResultLauncher();
        setupAddPhoneFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_clear) {
            Toast.makeText(this, R.string.clear_toast, Toast.LENGTH_SHORT).show();
            phoneViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
    }

    private void setupPhoneAdapter() {
        phoneAdapter = new PhoneAdapter(this);
        binding.recyclerPhones.setAdapter(phoneAdapter);
        binding.recyclerPhones.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupPhoneViewModel() {
        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        phoneViewModel.getAll().observe(this, phones -> phoneAdapter.set(phones));
    }

    private void setupAddPhoneActivityResultLauncher() {
        addPhoneActivityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onAddPhoneActivityResult);
    }

    private void setupAddPhoneFab() {
        binding.fabAddPhone.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPhoneActivity.class);
            addPhoneActivityResultLauncher.launch(intent);
        });
    }

    private void onAddPhoneActivityResult(ActivityResult result) {
        Intent data = result.getData();
        if (result.getResultCode() != RESULT_OK || data == null) return;

        Phone phone = extractPhone(data);
        if (phone != null) phoneViewModel.insert(phone);
    }

    private Phone extractPhone(Intent data) {
        String manufacturer = data.getStringExtra(AddPhoneActivity.MANUFACTURER_KEY);
        String model = data.getStringExtra(AddPhoneActivity.MODEL_KEY);
        int androidVersion = data.getIntExtra(AddPhoneActivity.ANDROID_VERSION_KEY, 0);
        String website = data.getStringExtra(AddPhoneActivity.WEBSITE_KEY);

        if (manufacturer == null || model == null || androidVersion == 0 || website == null) {
            return null;
        }
        return new Phone(manufacturer, model, androidVersion, website);
    }
}