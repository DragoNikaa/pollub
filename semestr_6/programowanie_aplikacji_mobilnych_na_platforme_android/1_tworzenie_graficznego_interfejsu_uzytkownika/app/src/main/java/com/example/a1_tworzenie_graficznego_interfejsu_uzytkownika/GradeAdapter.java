package com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1_tworzenie_graficznego_interfejsu_uzytkownika.databinding.GradeRowBinding;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<Grade> grades;

    public GradeAdapter(Context context, List<Grade> grades) {
        layoutInflater = LayoutInflater.from(context);
        this.grades = grades;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GradeViewHolder(GradeRowBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        Grade grade = grades.get(position);
        holder.binding.textSubject.setText(grade.getSubject());
        checkRadio(holder.binding, grade.getValue());
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    private void checkRadio(@NonNull GradeRowBinding binding, double gradeValue) {
        if (gradeValue == 2) binding.radioGrade2.setChecked(true);
        else if (gradeValue == 3) binding.radioGrade3.setChecked(true);
        else if (gradeValue == 3.5) binding.radioGrade35.setChecked(true);
        else if (gradeValue == 4) binding.radioGrade4.setChecked(true);
        else if (gradeValue == 4.5) binding.radioGrade45.setChecked(true);
        else if (gradeValue == 5) binding.radioGrade5.setChecked(true);
        else binding.radioGrades.clearCheck();
    }

    public class GradeViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        public GradeRowBinding binding;

        public GradeViewHolder(GradeRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.radioGrades.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
            Grade grade = grades.get(getBindingAdapterPosition());
            if (checkedId == -1) grade.setDefaultValue();
            else {
                double gradeValue = Double.parseDouble(group.findViewById(checkedId).getTag().toString());
                grade.setValue(gradeValue);
            }
        }
    }
}