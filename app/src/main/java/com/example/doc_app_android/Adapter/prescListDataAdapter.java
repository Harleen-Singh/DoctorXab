package com.example.doc_app_android.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.Pat_prescription;
import com.example.doc_app_android.databinding.SingleListPrescBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class prescListDataAdapter extends RecyclerView.Adapter<prescListDataAdapter.ViewHolder> {
//    private ArrayList<PrescData> data = new ArrayList<>();
public ArrayList<Pat_prescription> data;
    private int itemCount =4 ;
    public prescListDataAdapter(ArrayList<Pat_prescription> prescData){
        data = prescData; // contains Arraylist of prescription only
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SingleListPrescBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_list_presc,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull prescListDataAdapter.ViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: "+position);
        holder.Binding.setPrescription(data.get(position));
    }

    public void updateItemCount(){
        itemCount = data.size();
        notifyItemRangeInserted(4,data.size());
//        notifyItemChanged(4);
    }


    @Override
    public int getItemCount() {
        if(itemCount>data.size())
            return data.size();
        else
            return itemCount;
    }

    public void setDefaultItemCount() {
        itemCount = 4;
//        notifyItemRangeRemoved(4,data.size()-4);
        notifyItemRangeChanged(getItemCount(), data.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        SingleListPrescBinding Binding;
        public ViewHolder(@NonNull @NotNull SingleListPrescBinding binding) {
            super(binding.getRoot());
            this.Binding = binding;
        }
    }
}
