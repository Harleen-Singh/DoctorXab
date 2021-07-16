package com.example.doc_app_android.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.PrescData;
import com.example.doc_app_android.databinding.ListPrescBinding;
import com.example.doc_app_android.databinding.SingleListPrescBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class prescListDataAdapter extends RecyclerView.Adapter<prescListDataAdapter.ViewHolder> {
    private ArrayList<PrescData> data = new ArrayList<>();

    public prescListDataAdapter(){
        // required
    }

    public void setData(ArrayList<PrescData> d){this.data = d;}


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SingleListPrescBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_list_presc,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull prescListDataAdapter.ViewHolder holder, int position) {
        holder.Binding.setPrescData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        SingleListPrescBinding Binding;
        public ViewHolder(@NonNull @NotNull SingleListPrescBinding binding) {
            super(binding.getRoot());
            this.Binding = binding;
        }
    }
}
