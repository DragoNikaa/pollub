package com.example.a2_trwale_przechowywanie_danych;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2_trwale_przechowywanie_danych.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private PhoneAdapter phoneAdapter;
    private PhoneViewModel phoneViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setupToolbar();
        setupPhoneAdapter();
        setupPhoneViewModel();
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
}