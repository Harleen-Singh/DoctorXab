package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;
import com.example.doc_app_android.databinding.SpecialitySingleRowBinding;

import java.util.ArrayList;

public class SpecialistAdapter extends RecyclerView.Adapter<SpecialistAdapter.SpecialistViewHolder> {

    private ArrayList<String> specilaities;
    private Context context;

    public SpecialistAdapter(ArrayList<String> specilaities, Context context) {
        this.specilaities = specilaities;
        this.context = context;
    }

    @NonNull
    @Override
    public SpecialistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SpecialitySingleRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.speciality_single_row, parent, false);
        return new SpecialistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialistViewHolder holder, int position) {
        holder.binding.doctorSpeciality.setText(specilaities.get(position));
    }

    @Override
    public int getItemCount() {
        return specilaities.size();
    }

    class SpecialistViewHolder extends RecyclerView.ViewHolder {
        SpecialitySingleRowBinding binding;

        public SpecialistViewHolder(@NonNull SpecialitySingleRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
