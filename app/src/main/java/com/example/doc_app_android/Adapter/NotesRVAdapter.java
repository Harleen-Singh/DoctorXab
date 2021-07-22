package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.Xray_data;
import com.example.doc_app_android.databinding.SingleListNotesBinding;
import com.example.doc_app_android.databinding.SingleListXrayBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NotesRVAdapter extends RecyclerView.Adapter<NotesRVAdapter.NotesVH> {

    private ArrayList<String> message = new ArrayList<>();
    private Context mContext;

    public NotesRVAdapter(Context context){
        this.mContext = context;
    }

    public void setdata(ArrayList<String> msg){
        this.message=msg;
    }

    @Override
    public NotesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        SingleListNotesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_list_notes,parent,false);
        return new NotesVH(binding);
    }

    @Override
    public void onBindViewHolder(NotesRVAdapter.NotesVH holder, int position) {
        holder.singleListNotesBinding.tvNotes.setText(message.get(position));
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class NotesVH extends RecyclerView.ViewHolder{
        SingleListNotesBinding singleListNotesBinding;
        public NotesVH(SingleListNotesBinding binding) {
            super(binding.getRoot());
            this.singleListNotesBinding = binding;
        }
    }
}
