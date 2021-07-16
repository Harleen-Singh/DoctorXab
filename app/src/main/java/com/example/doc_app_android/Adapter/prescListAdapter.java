package com.example.doc_app_android.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.PrescData;
import com.example.doc_app_android.databinding.ChkUpHistoryBinding;
import com.example.doc_app_android.databinding.ListPrescBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class prescListAdapter extends RecyclerView.Adapter<prescListAdapter.ViewHolder> {
 private ArrayList<PrescData> data;
 private Activity activity;

 public prescListAdapter(){
     // required
 }

    public void setData(ArrayList<PrescData> d){this.data = d;}


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ListPrescBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_presc,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull prescListAdapter.ViewHolder holder, int position) {
        holder.Binding.setPrescData(data.get(position));
        prescListDataAdapter childAdapter = new prescListDataAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        holder.Binding.listRview.setLayoutManager(layoutManager);
        holder.Binding.listRview.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
     ListPrescBinding Binding;
        public ViewHolder(@NonNull @NotNull ListPrescBinding binding) {
            super(binding.getRoot());
            this.Binding = binding;
        }
    }
}
