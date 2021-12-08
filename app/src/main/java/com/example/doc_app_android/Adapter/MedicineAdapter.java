package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.MedicineList;
import com.example.doc_app_android.databinding.PrescriptionSingleRowBinding;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private ArrayList<MedicineList> medicine_list;
    private Context context;

    public MedicineAdapter(ArrayList<MedicineList> medicine_list, Context context) {
        this.medicine_list = medicine_list;
        this.context = context;
    }

    public void setData(ArrayList<MedicineList> medicine_list) {
        this.medicine_list = medicine_list;
    }

    public ArrayList<MedicineList> getData() {
        return medicine_list;
    }


    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PrescriptionSingleRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.prescription_single_row, parent, false);
        return new MedicineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        holder.binding.editPrescription.setText(medicine_list.get(position).getMeds_name());
        holder.binding.editMedsQuantity.setText(medicine_list.get(position).getQuantity());

        holder.binding.moreMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicine_list.remove(holder.getAbsoluteAdapterPosition());
                MedicineAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicine_list.size();
    }

    protected class MedicineViewHolder extends RecyclerView.ViewHolder {

        PrescriptionSingleRowBinding binding;

        public MedicineViewHolder(@NonNull PrescriptionSingleRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
