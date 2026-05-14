package com.example.a2_trwale_przechowywanie_danych;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2_trwale_przechowywanie_danych.databinding.PhoneRowBinding;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<Phone> phones;

    public PhoneAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        phones = null;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhoneViewHolder(PhoneRowBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        holder.bindToPhoneViewHolder(position);
    }

    @Override
    public int getItemCount() {
        if (phones == null) return 0;
        return phones.size();
    }

    public void set(List<Phone> phones) {
        this.phones = phones;
        notifyDataSetChanged();
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder {
        public PhoneRowBinding binding;

        public PhoneViewHolder(PhoneRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindToPhoneViewHolder(int position) {
            Phone phone = phones.get(position);
            binding.textManufacturer.setText(phone.getManufacturer());
            binding.textModel.setText(phone.getModel());
        }
    }
}