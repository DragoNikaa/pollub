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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2_trwale_przechowywanie_danych.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private PhoneAdapter phoneAdapter;
    private PhoneViewModel phoneViewModel;

    private ActivityResultLauncher<Intent> addEditPhoneActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setupToolbar();
        setupAddEditPhoneActivityResultLauncher();
        setupAddPhoneFab();
        setupPhoneAdapter();
        setupPhoneViewModel();
        setupItemTouchHelper();
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

    private void setupAddEditPhoneActivityResultLauncher() {
        addEditPhoneActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onAddEditPhoneActivityResult
        );
    }

    private void onAddEditPhoneActivityResult(ActivityResult result) {
        Intent data = result.getData();
        if (result.getResultCode() != RESULT_OK || data == null) return;

        long phoneId = data.getLongExtra(AddEditPhoneActivity.PHONE_ID_KEY, -1);
        Phone phone = extractPhone(data, phoneId == -1 ? null : phoneId);
        if (phone == null) return;

        if (phoneId == -1) phoneViewModel.insert(phone);
        else phoneViewModel.update(phone);
    }

    private Phone extractPhone(Intent data, Long id) {
        String manufacturer = data.getStringExtra(AddEditPhoneActivity.MANUFACTURER_KEY);
        String model = data.getStringExtra(AddEditPhoneActivity.MODEL_KEY);
        int androidVersion = data.getIntExtra(AddEditPhoneActivity.ANDROID_VERSION_KEY, 0);
        String website = data.getStringExtra(AddEditPhoneActivity.WEBSITE_KEY);

        if (manufacturer == null || model == null || androidVersion == 0 || website == null) {
            return null;
        }
        return id == null
                ? new Phone(manufacturer, model, androidVersion, website)
                : new Phone(id, manufacturer, model, androidVersion, website);
    }

    private void setupAddPhoneFab() {
        binding.fabAddPhone.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditPhoneActivity.class);
            addEditPhoneActivityResultLauncher.launch(intent);
        });
    }

    private void setupPhoneAdapter() {
        phoneAdapter = new PhoneAdapter(
                this, phone -> addEditPhoneActivityResultLauncher.launch(createEditIntent(phone))
        );
        binding.recyclerPhones.setAdapter(phoneAdapter);
        binding.recyclerPhones.setLayoutManager(new LinearLayoutManager(this));
    }

    private Intent createEditIntent(Phone phone) {
        return new Intent(this, AddEditPhoneActivity.class)
                .putExtra(AddEditPhoneActivity.PHONE_ID_KEY, phone.getId())
                .putExtra(AddEditPhoneActivity.MANUFACTURER_KEY, phone.getManufacturer())
                .putExtra(AddEditPhoneActivity.MODEL_KEY, phone.getModel())
                .putExtra(AddEditPhoneActivity.ANDROID_VERSION_KEY, phone.getAndroidVersion())
                .putExtra(AddEditPhoneActivity.WEBSITE_KEY, phone.getWebsite());
    }

    private void setupPhoneViewModel() {
        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        phoneViewModel.getAll().observe(this, phones -> phoneAdapter.set(phones));
    }

    private void setupItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        Phone phone = phoneAdapter.getPhoneAt(viewHolder.getBindingAdapterPosition());
                        phoneViewModel.delete(phone);
                    }
                });
        itemTouchHelper.attachToRecyclerView(binding.recyclerPhones);
    }
}